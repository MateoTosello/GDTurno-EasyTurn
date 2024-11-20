package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.AppointmentShortDTO;
import com.backend.easyturn.entities.DTOs.DiagnosisDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Diagnosis {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDiagnosis;

    @Column()
    private String diagnosisDescription;

    @OneToOne(mappedBy = "diagnosis")
    private Appointment appointment;

    public DiagnosisDTO toDTO() {
        DiagnosisDTO dto = new DiagnosisDTO();
        dto.setDiagnosisId(this.idDiagnosis);
        dto.setDiagnosisDescription(this.diagnosisDescription);

        if (this.appointment != null) {
            AppointmentShortDTO appointmentShortDTO = new AppointmentShortDTO(
                    this.appointment.getIdAppointment(),
                    this.appointment.getAppointmentDateTime(),
                    this.appointment.getPatient().toShortDTO()
            );
            dto.setAppointment(appointmentShortDTO);
        }
        return dto;
    }
}
