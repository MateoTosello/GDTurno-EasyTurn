package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentShortDTO {
    private int idAppointment;
    private LocalDateTime appointmentDateTime;
    private PatientShortDTO patient;

    public AppointmentShortDTO(int idAppointment, LocalDateTime appointmentDateTime, PatientShortDTO patient) {
        this.idAppointment = idAppointment;
        this.appointmentDateTime = appointmentDateTime;
        this.patient = patient;

    }
}
