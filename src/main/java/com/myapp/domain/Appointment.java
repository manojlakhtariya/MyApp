package com.myapp.domain;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private User organizer;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> attendees = new TreeSet<>();

    private AppointmentStatus appointmentStatus;
    public enum AppointmentStatus {SCHEDULED, CANCELLED}

    public String description;

    public Appointment(){}

    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, User organizer, Set<User> attendees, AppointmentStatus appointmentStatus, String description) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.attendees = attendees;
        this.appointmentStatus = appointmentStatus;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
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

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(organizer, that.organizer) &&
                Objects.equals(attendees, that.attendees) &&
                appointmentStatus == that.appointmentStatus &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, organizer, attendees, appointmentStatus, description);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", organizer=" + organizer +
                ", attendees=" + attendees +
                ", appointmentStatus=" + appointmentStatus +
                ", description='" + description + '\'' +
                '}';
    }
}
