package com.myapp.repository;

import com.myapp.domain.Appointment;
import com.myapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    @Query(value = "SELECT (COUNT(a.id) >=1 ) FROM appointment_attendees aa, appointment a WHERE aa.attendees_id IN :attendees and " +
            "aa.appointment_id = a.id and a.date = :dt and a.end_time > :st and a.start_time < :ft", nativeQuery = true)
    BigInteger conflict(@Param("attendees") Set<User> attendees,
                        @Param("dt") LocalDate date,
                        @Param("st") LocalTime startTime,
                        @Param("ft") LocalTime finishTime);
}
