package com.myapp.repository;

import com.myapp.domain.Appointment;
import com.myapp.domain.User;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>,
    JpaSpecificationExecutor<Appointment> {

  @Query(value =
      "SELECT (COUNT(a.id) >=1 ) FROM appointment_attendees aa, appointment a WHERE aa.attendees_id IN :attendees and "
          +
          "aa.appointment_id = a.id and a.date = :dt and a.end > :st and a.start < :ft", nativeQuery = true)
  BigInteger conflict(@Param("attendees") Set<User> attendees,
      @Param("dt") LocalDate date,
      @Param("st") LocalTime startTime,
      @Param("ft") LocalTime finishTime);

  @Query(value =
      "SELECT DISTINCT a.start, a.end FROM appointment_attendees aa, appointment a WHERE aa.attendees_id IN :attendees and "
          +
          "aa.appointment_id = a.id and a.date = :dt order by a.start", nativeQuery = true)
  List<Object[]> listAllAppointents(@Param("dt") LocalDate date,
      @Param("attendees") Set<User> attendees);

}
