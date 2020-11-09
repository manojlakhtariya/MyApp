package com.myapp.service;

import com.myapp.domain.Appointment;
import com.myapp.domain.TimeSlot;
import com.myapp.domain.User;
import com.myapp.error.BadRequestException;
import com.myapp.error.RecordNotFoundException;
import com.myapp.repository.AppointmentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppointmentServiceImpl implements AppointmentService {

  @Autowired
  UserService userService;
  AppointmentRepository appointmentRepository;

  public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public Appointment createAppointment(Appointment appointment) {
    validate(appointment);
    if (appointmentRepository.conflict(appointment.getAttendees(), appointment.getDate(),
        appointment.getTimeSlot().getStart(), appointment.getTimeSlot().getEnd()).intValue() >= 1) {
      throw new BadRequestException("Users have conflict");
    }
    return appointmentRepository.save(appointment);
  }

  @Override
  public Appointment getAppointment(Long id) {
    return appointmentRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("Appointment", id));
  }

  /**
   * find list of avaialble timeslot for all users in a day
   * @param date
   * @param attendees
   * @return
   */
  @Override
  public List<TimeSlot> getRecommandations(LocalDate date, Set<User> attendees) {
    List<TimeSlot> timeSlots = mergeTimeSlots(
        appointmentRepository.listAllAppointents(date, attendees));
    return findFreeTimeSlots(timeSlots);
  }

  @Override
  public Appointment updateUsers(Long id, Set<User> attendees) {
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("appointment", id));

    validateUsers(attendees);

    if (appointmentRepository
        .conflict(attendees, appointment.getDate(), appointment.getTimeSlot().getStart(),
            appointment.getTimeSlot().getEnd()).intValue() >= 1) {
      throw new BadRequestException("Users have conflict");
    }

    appointment.getAttendees().addAll(attendees);

    return appointmentRepository.save(appointment);
  }

  private void validate(Appointment appointment) {

    if (appointment.getAttendees().isEmpty()) {
      throw new BadRequestException("Please provide attendee list");
    }

    validateUsers(new HashSet<User>() {{
      add(appointment.getOrganizer());
    }});
    validateUsers(appointment.getAttendees());

    if (appointment.getTimeSlot().getStart().compareTo(appointment.getTimeSlot().getEnd()) > 0) {
      throw new BadRequestException("Start time must be less than end time ");
    }

  }

  private void validateUsers(Set<User> users) {
    for (User attendee : users) {
      if (!userService.findById(attendee.getId()).isPresent()) {
        throw new BadRequestException("Unable to find user id " + attendee.getId());
      }
    }
  }

  /**
   * find free time slots in a day
   * @param mergedSlots
   * @return
   */
  private List<TimeSlot> findFreeTimeSlots(List<TimeSlot> mergedSlots) {
    List<TimeSlot> freeTimeSlots = new ArrayList<>();
    TimeSlot previous = new TimeSlot(LocalTime.MIDNIGHT, LocalTime.MIDNIGHT);

    for (int i = 0; i < mergedSlots.size(); i++) {
      freeTimeSlots.add(new TimeSlot(previous.getEnd(), mergedSlots.get(i).getStart()));
      previous = mergedSlots.get(i);
    }

    if (previous.getEnd().compareTo(LocalTime.MIDNIGHT.minusMinutes(1)) <= 0) {
      freeTimeSlots.add(new TimeSlot(previous.getEnd(), LocalTime.MIDNIGHT));
    }

    return freeTimeSlots;
  }

  /**
   * merge list of time slots
   * @param timeSlots
   * @return
   */
  private List<TimeSlot> mergeTimeSlots(List<Object[]> timeSlots) {
    if (timeSlots.size() == 0 || timeSlots.size() == 1) {
      return Collections.emptyList();
    }

    LocalTime start = null;
    LocalTime end = null;

    List<TimeSlot> result = new ArrayList<>();

    for (int i = 0; i < timeSlots.size(); i++) {
      java.sql.Time startTime = (java.sql.Time) timeSlots.get(i)[0];
      java.sql.Time endTime = (java.sql.Time) timeSlots.get(i)[1];

      TimeSlot current = new TimeSlot(startTime.toLocalTime(), endTime.toLocalTime());

      if (i == 0) {
        start = current.getStart();
        end = current.getEnd();
      }

      if (current.getStart().compareTo(end) < 0) {
        end = (current.getEnd().compareTo(end) > 0) ? current.getEnd() : end;
      } else {
        result.add(new TimeSlot(start, end));
        start = current.getStart();
        end = current.getEnd();
      }
    }

    result.add(new TimeSlot(start, end));
    return result;
  }

}
