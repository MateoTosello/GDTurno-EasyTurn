package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByPatientMail(String mail);
}
