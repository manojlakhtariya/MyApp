package com.myapp.service;

import com.myapp.domain.Appointment;
import com.myapp.domain.TimeSlot;
import com.myapp.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AppointmentService {

  Appointment createAppointment(Appointment appointment);

  Appointment getAppointment(Long id);

  List<TimeSlot> getRecommandations(LocalDate date, Set<User> attendees);

  Appointment updateUsers(Long id, Set<User> attendees);
}
