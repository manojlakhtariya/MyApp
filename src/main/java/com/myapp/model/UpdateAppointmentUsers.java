package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myapp.domain.User;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateAppointmentUsers {

  private Set<User> attendees;

  @JsonCreator
  public UpdateAppointmentUsers(Set<User> attendees) {
    this.attendees = attendees;
  }

  public Set<User> getAttendees() {
    return attendees;
  }
}
