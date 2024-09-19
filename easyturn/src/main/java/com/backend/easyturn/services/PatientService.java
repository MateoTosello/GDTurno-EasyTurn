package com.backend.easyturn.services;

import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.HealthInsuranceRepository;
import com.backend.easyturn.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;

    public Patient createPatient(Patient patient, int idHealthInsurance) {
        try{
            Patient p = this.patientRepository.findByMail(patient.getMail());
            if(p != null) {
                throw new AppException("El paciente ya existe", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(idHealthInsurance)
                    .orElseThrow(() -> new AppException("La OS no existe", HttpStatus.NOT_FOUND));
            patient.setHealthInsurance(healthInsurance);

            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Patient getPatientById(int idPatient){
        try {
            return this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new AppException("El paciente no existe", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Patient> getAllPatients(){
        try {
            List<Patient> patients = this.patientRepository.findAll();
            if (patients.isEmpty()) {
                throw new AppException("No hay pacientes cargados", HttpStatus.NOT_FOUND);
            }
            return patients;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Patient updatePatient(Patient patient,  int idHealthInsurance){
        try {
            Patient p = this.patientRepository.findById(patient.getIdPatient())
                    .orElseThrow(() -> new AppException("Paciente no encontrado", HttpStatus.NOT_FOUND));
            p.setFirstName(patient.getFirstName());
            p.setLastName(patient.getLastName());
            p.setMail(patient.getMail());
            p.setIdCardNumber(patient.getIdCardNumber());
            p.setPhoneNumber(patient.getPhoneNumber());

            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(idHealthInsurance)
                    .orElseThrow(() -> new AppException("La Obra Social no existe", HttpStatus.NOT_FOUND));
            p.setHealthInsurance(healthInsurance);

            return patientRepository.save(p);
        } catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deletePatient(int idPatient){
        try{
            Patient p = this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new AppException("El paciente no existe", HttpStatus.NOT_FOUND));
            this.patientRepository.delete(p);
        } catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}