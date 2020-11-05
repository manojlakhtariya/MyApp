package com.myapp.service;

import com.myapp.domain.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);

    Appointment getAppointment(Long id);

    Page<Appointment> listAllAppointments(Long userId, LocalDate startTime, LocalDate endTime, Integer page, Integer pageSize);
}
