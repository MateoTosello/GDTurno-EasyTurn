package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Long createAppointment (Appointment appointment) {
        Appointment apo = this.appointmentRepository.save(appointment);
        return apo.getIdAppointment();
    }

    public List<Appointment> getAllAppointments(){
        return this.appointmentRepository.findAll();
    }

    public Appointment getAppointment(int id) {
        return this.appointmentRepository.findById(id).get();
    }

    public void deleteAppointment (Appointment appointment) {
        this.appointmentRepository.delete(appointment);
    }

    public void deleteAppointmentById(int id) {
        this.appointmentRepository.deleteById(id);
    }
    public Appointment updateAppointment (Appointment appointment) {
        return this.appointmentRepository.save(appointment);
    } //creo que reemplaza el existente por id --> ver porque no estoy seguro (otra opcion es traerlo por id, setear los datos nuevos y volverlo a guardar)



}
