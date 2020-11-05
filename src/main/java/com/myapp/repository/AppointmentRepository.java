package com.myapp.repository;

import com.myapp.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.myapp.domain.User;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    @Query(value = "SELECT (COUNT(a) >=1 ) FROM appointment_attendees a, appointment b WHERE a.attendees_id IN :attendees and "+
            "a.appointment_id = b.id and appointment.date = :dt and appointment.finishTime > :st and appointment.startTime < :ft", nativeQuery = true)
    boolean conflict(@Param("attendees") Set<User> attendees,
                                 @Param("dt") LocalDate date,
                                 @Param("st") LocalTime startTime,
                                 @Param("ft") LocalTime finishTime);
}
