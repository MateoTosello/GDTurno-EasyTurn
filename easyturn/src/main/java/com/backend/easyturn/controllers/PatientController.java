package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.AppointmentDTO;
import com.backend.easyturn.entities.DTOs.PatientDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.requests.PatientRequest;
import com.backend.easyturn.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @CrossOrigin(origins = "*")
    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientRequest request){
        Patient patientCreated = this.patientService.createPatient(request.getPatient(), request.getIdHealthInsurance());
        return new ResponseEntity<>(patientCreated.toDTO(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-patient/{id}")
    @ResponseBody
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable int id){
        Patient patient = this.patientService.getPatientById(id);
        return  new ResponseEntity<>(patient.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-patients")
    @ResponseBody
    public ResponseEntity<List<PatientDTO>> getAll(){
        List<Patient> patients = this.patientService.getAllPatients();
        List<PatientDTO> patientDTOs = patients.stream() //Un stream es una secuencia de elementos que permite aplicar operaciones funcionales, como transformar, filtrar, o recolectar datos.
                .map(Patient::toDTO) //La operación map() toma cada objeto del stream (en este caso, cada objeto Professional) y lo transforma usando el método toDTO()
                .toList();
        return new ResponseEntity<>(patientDTOs, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update-patient")
    @ResponseBody
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientRequest request){
        Patient patientUpdated = this.patientService.updatePatient(request.getPatient(), request.getIdHealthInsurance());
        return new ResponseEntity<>(patientUpdated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete-patient/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletePatient(@PathVariable int id){
        this.patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/appointments/{id}")
    @ResponseBody
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(@PathVariable int id) {
        Set<Appointment> appointments = this.patientService.getAppointmentsByPatient(id);
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(Appointment::toDTO)
                .toList();
        return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
    }

}