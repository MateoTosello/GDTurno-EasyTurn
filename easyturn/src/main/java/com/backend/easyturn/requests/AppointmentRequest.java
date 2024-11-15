package com.backend.easyturn.requests;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Patient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {

    private Appointment appointment;  // Fecha y hora del turno + estado (null) + valoracion (null)
    private int idPatient;                    // ID del paciente
    private int idProfessional;               // ID del profesional
    private Long idSpeciality;                 // ID de la especialidad

}





