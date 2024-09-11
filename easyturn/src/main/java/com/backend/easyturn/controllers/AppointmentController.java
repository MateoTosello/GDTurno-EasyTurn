package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/getAppointments")
    @ResponseBody
    public List<Appointment> getAppointments() {
        return this.appointmentService.getAllAppointments();
    }

    @GetMapping("/getAppointment/{id}")
    @ResponseBody
    public Appointment getAppointment(@PathVariable int id) {
        return this.appointmentService.getAppointment(id);
    }

    @PostMapping("/appointment")
    @ResponseBody
    public Long newAppointment(@RequestBody Appointment appointment) {
        return this.appointmentService.createAppointment(appointment);
    }

    @DeleteMapping("/appointment/{id}")
    public void deleteAppointmentById(@PathVariable int id) {
        this.appointmentService.deleteAppointmentById(id);
    }

    @DeleteMapping("/appointment")
    public void deleteAppointment(@RequestBody Appointment appointment) {
        this.appointmentService.deleteAppointment(appointment);
    }

    @PutMapping("/updateAppointment")
    @ResponseBody
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        return this.appointmentService.updateAppointment(appointment);
    }
}
