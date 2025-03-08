package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.PatientDTO;
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
            if(this.patientRepository.findByMail(patient.getMail()) != null) {
                throw new IfClassExistsException("El paciente con el email:"+patient.getMail()+" ya existe");
            }
            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(idHealthInsurance)
                    .orElseThrow(() -> new NotFoundException("La OS no existe"));
            patient.setHealthInsurance(healthInsurance);
            if(this.patientRepository.findByIdCardNumber(patient.getIdCardNumber()) != null) {
                throw new IfClassExistsException("El paciente con el id de afiliado:"+patient.getIdCardNumber()+" ya existe");
            }
            if(this.patientRepository.findByPhoneNumber(patient.getPhoneNumber()) != null) {
                throw new IfClassExistsException("El paciente con el telefono:"+patient.getPhoneNumber()+" ya existe");
            }
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

    public List<PatientDTO> getAllPatients(){
        try {
            List<Patient> patients = this.patientRepository.findAll();
            if (patients.isEmpty()) {
                throw new NotFoundException("No hay pacientes cargados");
            }
            List<PatientDTO> patientDTOs = patients.stream() //Un stream es una secuencia de elementos que permite aplicar operaciones funcionales, como transformar, filtrar, o recolectar datos.
                    .map(Patient::toDTO) //La operación map() toma cada objeto del stream (en este caso, cada objeto Professional) y lo transforma usando el método toDTO()
                    .toList();
            return patientDTOs;
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

    public Patient getByEmail(String email) {
        try{
            Patient patient = this.patientRepository.findByMail(email);
            if (patient == null) {
                throw new NotFoundException("El paciente con el email: "+email+"  no existe");
            }
            return patient;
        }
        catch (Exception e){
            throw new AppException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}