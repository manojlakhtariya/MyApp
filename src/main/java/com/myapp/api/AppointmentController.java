package com.myapp.api;

import com.myapp.domain.Appointment;
import com.myapp.model.AppointmentRecommandationRequest;
import com.myapp.model.AppointmentRecommandationResponse;
import com.myapp.model.AppointmentRequest;
import com.myapp.model.AppointmentResponse;
import com.myapp.model.UpdateAppointmentUsers;
import com.myapp.service.AppointmentService;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @PostMapping("/appointments")
  public ResponseEntity<AppointmentResponse> createAppointment(
      @Valid @RequestBody AppointmentRequest appointmentRequest) {
    Appointment newAppointment = appointmentService
        .createAppointment(getAppointmentFromRequest(appointmentRequest));
    return new ResponseEntity<>(
        new AppointmentResponse(newAppointment.getId(), newAppointment.getDate(),
            appointmentRequest.getTimeSlot(), newAppointment.getOrganizer(),
            newAppointment.getAttendees(), newAppointment.getDescription()), HttpStatus.OK);
  }

  @GetMapping("/appointments/{appointment_id}")
  public ResponseEntity<AppointmentResponse> findAppointmentById(
      @PathVariable("appointment_id") Long appointment_id) {
    Appointment appointment = appointmentService.getAppointment(appointment_id);
    return new ResponseEntity<>(new AppointmentResponse(appointment.getId(), appointment.getDate(),
        appointment.getTimeSlot(), appointment.getOrganizer(), appointment.getAttendees(),
        appointment.getDescription()), HttpStatus.OK);
  }

  @GetMapping("appointments/availability")
  public ResponseEntity<AppointmentRecommandationResponse> listAvailability(
      @Valid @RequestBody AppointmentRecommandationRequest appointmentRequest) {
    return new ResponseEntity<>(new AppointmentRecommandationResponse(appointmentRequest.getDate(),
        appointmentService
            .getRecommandations(appointmentRequest.getDate(), appointmentRequest.getAttendees())),
        HttpStatus.OK);
  }

  @PutMapping("/appointments/{appointment_id}")
  public ResponseEntity<AppointmentResponse> updateUsers(
      @PathVariable("appointment_id") Long appointment_id,
      @Valid @RequestBody UpdateAppointmentUsers updateAppointmentUsers) {
    Appointment newAppointment = appointmentService
        .updateUsers(appointment_id, updateAppointmentUsers.getAttendees());
    ResponseEntity<AppointmentResponse> result = new ResponseEntity<>(
        new AppointmentResponse(newAppointment.getId(), newAppointment.getDate(),
            newAppointment.getTimeSlot(), newAppointment.getOrganizer(),
            newAppointment.getAttendees(), newAppointment.getDescription()), HttpStatus.OK);
    return result;
  }

  private Appointment getAppointmentFromRequest(AppointmentRequest appointmentRequest) {
    return new Appointment(LocalDate.now(), appointmentRequest.getTimeSlot(),
        appointmentRequest.getOrganizer(), appointmentRequest.getAttendees(),
        Appointment.AppointmentStatus.SCHEDULED, appointmentRequest.getDescritpion());
  }


}
