package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.domain.TimeSlot;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRecommandationResponse {

  private final LocalDate date;
  private final List<TimeSlot> timeSlotList;

  @JsonCreator
  public AppointmentRecommandationResponse(@JsonProperty("date") LocalDate date,
      @JsonProperty("timeslots") List<TimeSlot> timeSlotList) {
    this.date = date;
    this.timeSlotList = timeSlotList;
  }

  public LocalDate getDate() {
    return date;
  }

  public List<TimeSlot> getTimeSlotList() {
    return timeSlotList;
  }
}
