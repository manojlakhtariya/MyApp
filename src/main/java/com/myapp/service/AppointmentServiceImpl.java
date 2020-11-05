package com.myapp.service;

import com.myapp.domain.Appointment;
import com.myapp.error.BadRequestException;
import com.myapp.error.RecordNotFoundException;
import com.myapp.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        if (appointmentRepository.conflict(appointment.getAttendees(), appointment.getDate(), appointment.getStartTime(), appointment.getEndTime()).intValue() >= 1) {
            throw new BadRequestException("Users have conflict");
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Appointment", id));
    }

    @Override
    public Page<Appointment> listAllAppointments(Long userId, LocalDate startTime, LocalDate endTime, Integer page, Integer pageSize) {
        Specification<Appointment> startTimeSpec = ((root, criteriaQuery, criteriaBuilder) -> startTime == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startTime));
        Specification<Appointment> endTimeSpec = (((root, criteriaQuery, criteriaBuilder) -> endTime == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("date"), endTime)));
        Specification<Appointment> userSpec = (((root, criteriaQuery, criteriaBuilder) -> userId == null ? null : criteriaBuilder.equal(root.get("organizer").get("id"), userId)));

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);
        Sort sort = Sort.by("date").descending().and(Sort.by("startTime").ascending());
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return appointmentRepository.findAll(Specification.where(startTimeSpec).and(endTimeSpec).and(userSpec), pageable);
    }

}
