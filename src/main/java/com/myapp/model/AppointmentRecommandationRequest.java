package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.domain.User;
import java.time.LocalDate;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRecommandationRequest {

  private final LocalDate date;
  private final Set<User> attendees;

  @JsonCreator
  public AppointmentRecommandationRequest(@JsonProperty("date") LocalDate date,
      @JsonProperty("attendees") Set<User> attendees) {
    this.date = date;
    this.attendees = attendees;
  }

  public LocalDate getDate() {
    return date;
  }

  public Set<User> getAttendees() {
    return attendees;
  }
}
