package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    boolean existsByProfessionalAndAppointmentDateTime(Professional professional, LocalDateTime appointmentDateTime);
}
