package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.domain.TimeSlot;
import com.myapp.domain.User;
import java.util.Set;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRequest {

  @NotNull
  private final TimeSlot timeSlot;
  @NotNull
  private final User organizer;
  @NotNull
  private final Set<User> attendees;

  private final String descritpion;


  @JsonCreator
  public AppointmentRequest(@JsonProperty("timeSlot") TimeSlot timeSlot,
      @JsonProperty("organizer") User organizer,
      @JsonProperty("attendees") Set<User> attendees,
      @JsonProperty("description") String descritpion) {
    this.timeSlot = timeSlot;
    this.organizer = organizer;
    this.attendees = attendees;
    this.descritpion = descritpion;
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

  public TimeSlot getTimeSlot() {
    return timeSlot;
  }
}
