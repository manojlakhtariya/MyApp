package com.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment {

  public String description;
  @Id
  @GeneratedValue
  private long id;
  private LocalDate date;
  @NotNull
  @OneToOne
  private User organizer;
  @NotNull
  @ManyToMany
  private Set<User> attendees = new TreeSet<>();
  @NotNull
  @Embedded
  private TimeSlot timeSlot;
  private AppointmentStatus appointmentStatus;

  public Appointment() {
  }

  public Appointment(LocalDate date, TimeSlot timeSlot, User organizer, Set<User> attendees,
      AppointmentStatus appointmentStatus, String description) {
    this.date = date;
    this.organizer = organizer;
    this.attendees = attendees;
    this.appointmentStatus = appointmentStatus;
    this.description = description;
    this.timeSlot = timeSlot;
  }

  public long getId() {
    return id;
  }

  public LocalDate getDate() {
    return date;
  }

  public User getOrganizer() {
    return organizer;
  }

  public Set<User> getAttendees() {
    return attendees;
  }

  public AppointmentStatus getAppointmentStatus() {
    return appointmentStatus;
  }

  public TimeSlot getTimeSlot() {
    return timeSlot;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Appointment that = (Appointment) o;
    return id == that.id &&
        Objects.equals(date, that.date) &&
        Objects.equals(organizer, that.organizer) &&
        Objects.equals(attendees, that.attendees) &&
        Objects.equals(timeSlot, that.timeSlot) &&
        appointmentStatus == that.appointmentStatus &&
        Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, organizer, attendees, timeSlot, appointmentStatus, description);
  }

  @Override
  public String toString() {
    return "Appointment{" +
        "id=" + id +
        ", date=" + date +
        ", organizer=" + organizer +
        ", attendees=" + attendees +
        ", timeSlot=" + timeSlot +
        ", appointmentStatus=" + appointmentStatus +
        ", description='" + description + '\'' +
        '}';
  }

  public enum AppointmentStatus {SCHEDULED, CANCELLED}
}
