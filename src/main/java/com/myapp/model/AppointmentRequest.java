package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.domain.User;

import java.time.LocalTime;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRequest {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final User organizer;
    private final Set<User> attendees;
    private final String descritpion;


    @JsonCreator
    public AppointmentRequest(@JsonProperty("startTime") LocalTime startTime,
                              @JsonProperty("endTime") LocalTime endTime,
                              @JsonProperty("organizer") User organizer,
                              @JsonProperty("attendees") Set<User> attendees,
                              @JsonProperty("description") String descritpion) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.attendees = attendees;
        this.descritpion = descritpion;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
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
}
