package com.backend.easyturn.entities.DTOs;

import com.backend.easyturn.entities.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
    private int idAppointment;
    private LocalDateTime appointment_datetime;
    private Appointment.AppointmentStatus status;
    private String valoration;
    private PatientShortDTO patient;
    private ProfessionalShortDTO professional;
    private SpecialityShortDTO speciality;
}
