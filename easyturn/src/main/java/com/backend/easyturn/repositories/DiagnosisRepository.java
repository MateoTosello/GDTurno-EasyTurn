package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis,Integer> {

    boolean existsByAppointmentIdAppointment(Integer appointmentId);
}
