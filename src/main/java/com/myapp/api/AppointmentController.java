package com.myapp.api;

import com.myapp.domain.Appointment;
import com.myapp.model.AppointmentRequest;
import com.myapp.model.AppointmentResponse;
import com.myapp.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        Appointment newAppointment = appointmentService.createAppointment(getAppointmentFromRequest(appointmentRequest));
        return new ResponseEntity<>(new AppointmentResponse(newAppointment.getDate(), newAppointment.getStartTime(), newAppointment.getEndTime(), newAppointment.getOrganizer(), newAppointment.getAttendees(), newAppointment.getDescription()), HttpStatus.OK);
    }

    @GetMapping("/appointments/{appointment_id}")
    public ResponseEntity<AppointmentResponse> findAppointmentById(@PathVariable("appointment_id") Long appointment_id) {
        Appointment appointment = appointmentService.getAppointment(appointment_id);
        return new ResponseEntity<>(new AppointmentResponse(appointment.getDate(), appointment.getStartTime(), appointment.getEndTime(), appointment.getOrganizer(), appointment.getAttendees(), appointment.getDescription()), HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public Page<Appointment> listAllAppointments(@RequestParam(required = false) Long user_id, @RequestParam(required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate, @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                         LocalDate maxDate, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return appointmentService.listAllAppointments(user_id, maxDate, maxDate, page, pageSize);
    }

    private Appointment getAppointmentFromRequest(AppointmentRequest appointmentRequest) {
        return new Appointment(LocalDate.now(), appointmentRequest.getStartTime(), appointmentRequest.getEndTime(), appointmentRequest.getOrganizer(), appointmentRequest.getAttendees(), Appointment.AppointmentStatus.SCHEDULED, appointmentRequest.getDescritpion());
    }


}
