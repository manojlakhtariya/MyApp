package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.domain.TimeSlot;
import com.myapp.domain.User;
import java.time.LocalDate;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponse {

  private final Long id;
  private final LocalDate date;
  private final TimeSlot timeSlot;
  private final User organizer;
  private final Set<User> attendees;
  private final String descritpion;


  @JsonCreator
  public AppointmentResponse(@JsonProperty("id") Long id,
      @JsonProperty("date") LocalDate date,
      @JsonProperty("timeslot") TimeSlot timeSlot,
      @JsonProperty("organizer") User organizer,
      @JsonProperty("attendees") Set<User> attendees,
      @JsonProperty("description") String descritpion) {
    this.id = id;
    this.date = date;
    this.timeSlot = timeSlot;
    this.organizer = organizer;
    this.attendees = attendees;
    this.descritpion = descritpion;
  }

  public LocalDate getDate() {
    return date;
  }

  public TimeSlot getTimeSlot() {
    return timeSlot;
  }

  public User getOrganizer() {
    return organizer;
  }

  public Set<User> getAttendees() {
    return attendees;
  }

  public String getDescritpion() {
    return descritpion;
  }

  public Long getId() {
    return id;
  }
}
