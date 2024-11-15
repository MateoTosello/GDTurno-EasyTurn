package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.AppointmentDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.requests.AppointmentRequest;
import com.backend.easyturn.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;


    @GetMapping("/get-all-appointments")
    @ResponseBody
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<Appointment> appointments = this.appointmentService.getAllAppointments();
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(Appointment::toDTO)
                .toList();
        return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
    }

    @GetMapping("/get-appointment/{id}")
    @ResponseBody
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable int id) {
        Appointment appointment = this.appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment.toDTO(), HttpStatus.OK);
    }

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentRequest request) {
        Appointment appointmentCreated = this.appointmentService.createAppointment(
                request.getAppointment(),
                request.getIdPatient(),
                request.getIdProfessional(),
                request.getIdSpeciality()
        );
        return new ResponseEntity<>(appointmentCreated.toDTO(), HttpStatus.CREATED);
    }


    @PutMapping("/update-appointment")
    @ResponseBody
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody Appointment appointment) {
        Appointment appointmentUpdated = this.appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(appointmentUpdated.toDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-appointment/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        this.appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
