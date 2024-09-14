package com.backend.easyturn.services;

import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient, Long idHealthInsurance) {
        try{
            Patient p = this.patientRepository.findByPatientMail(patient.getMail());
            if(p != null) {
                throw new AppException("El patient ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            HealthInsurance healthInsurance = new HealthInsurance();

            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
