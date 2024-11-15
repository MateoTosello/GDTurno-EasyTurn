package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.professional = :professional " +
            "AND a.appointmentDateTime BETWEEN :startTime AND :endTime")
    boolean existsByProfessionalAndAppointmentDateTimeBetween(
            @Param("professional") Professional professional,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
