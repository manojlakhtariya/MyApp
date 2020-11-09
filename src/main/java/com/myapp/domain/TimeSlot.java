package com.myapp.domain;


import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class TimeSlot extends Object {

  @NotNull
  @Column(columnDefinition = "TIME")
  private LocalTime start = null;
  @NotNull
  @Column(columnDefinition = "TIME")
  private LocalTime end = null;

  public TimeSlot(LocalTime start, LocalTime end) {
    this.start = start;
    this.end = end;
  }

  public TimeSlot() {
  }

  public LocalTime getStart() {
    return start;
  }

  public void setStart(LocalTime start) {
    this.start = start;
  }

  public LocalTime getEnd() {
    return end;
  }

  public void setEnd(LocalTime end) {
    this.end = end;
  }
}
