package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.repositories.HealthInsuranceRepository;
import com.backend.easyturn.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
                throw new IfClassExistsException("El paciente ya existe");
            }
            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(idHealthInsurance)
                    .orElseThrow(() -> new NotFoundException("La OS no existe"));
            patient.setHealthInsurance(healthInsurance);

            return patientRepository.save(patient);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Patient getPatientById(int idPatient){
        try {
            return this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new NotFoundException("El paciente no existe"));
        } catch (AppException e) {
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public List<Patient> getAllPatients(){
        try {
            List<Patient> patients = this.patientRepository.findAll();
            if (patients.isEmpty()) {
                throw new NotFoundException("No hay pacientes cargados");
            }
            return patients;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Patient updatePatient(Patient patient,  int idHealthInsurance){
        try {
            Patient p = this.patientRepository.findById(patient.getIdPatient())
                    .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));
            p.setFirstName(patient.getFirstName());
            p.setLastName(patient.getLastName());
            p.setMail(patient.getMail());
            p.setIdCardNumber(patient.getIdCardNumber());
            p.setPhoneNumber(patient.getPhoneNumber());

            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(idHealthInsurance)
                    .orElseThrow(() -> new NotFoundException("La Obra Social no existe"));
            p.setHealthInsurance(healthInsurance);

            return patientRepository.save(p);
        } catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public void deletePatient(int idPatient){
        try{
            Patient p = this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new NotFoundException("El paciente no existe"));
            this.patientRepository.delete(p);
        } catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Set<Appointment> getAppointmentsByPatient(int idPatient) {
        try {
            Patient patient = this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new AppException("El paciente no existe", HttpStatus.NOT_FOUND));
            return patient.getAppointments();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}