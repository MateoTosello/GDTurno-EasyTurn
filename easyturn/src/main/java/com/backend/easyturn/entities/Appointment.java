package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "appointment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_APPOINTMENT_DATETIME_PROF_PATIENT",
                        columnNames = {"appointmentDateTime", "idProfessional", "idPatient"}
                )
        }
)
public class Appointment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppointment;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)  // Usar enum para status
    @Column(nullable = false)
    private AppointmentStatus appointmentStatus;

    @Column(length = 1000)  // Limitar longitud de campo de texto. Analizar si ponemos estrellas que provean la valoracion como "4: muy bueno"
    private String patientValoration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPatient", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProfessional", nullable = false)
    private Professional professional;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDiagnosis")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "idSpeciality", nullable = false)
    private Speciality speciality;

    // Enum para estado del turno
    public enum AppointmentStatus {
        SCHEDULED,
        CONFIRMED,
        CANCELLED,
        COMPLETED,
        NO_SHOW
    }

    public AppointmentDTO toDTO() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setIdAppointment(this.idAppointment);
        dto.setAppointment_datetime(this.appointmentDateTime);
        dto.setStatus(this.appointmentStatus);
        dto.setValoration(this.patientValoration);

        if (this.patient != null) {
            PatientShortDTO patientDTO = new PatientShortDTO(
                    this.patient.getIdPatient(),
                    this.patient.getFirstName(),
                    this.patient.getLastName()
            );
            dto.setPatient(patientDTO);
        }

        if (this.professional != null) {
            ProfessionalShortDTO professionalDTO = new ProfessionalShortDTO(
                    this.professional.getIdProfessional(),
                    this.professional.getProfessionalName()
            );
            dto.setProfessional(professionalDTO);
        }

        if (this.speciality != null) {
            SpecialityShortDTO specialityDTO = new SpecialityShortDTO(
                    this.speciality.getIdSpeciality(),
                    this.speciality.getSpecialityName()
            );
            dto.setSpeciality(specialityDTO);
        }
        return dto;
    }

    public AppointmentShortDTO toShortDTO() {
        // Convertimos el paciente a PatientShortDTO
        PatientShortDTO patientShortDTO = this.patient.toShortDTO();

        // Creamos y retornamos el nuevo AppointmentShortDTO
        return new AppointmentShortDTO(
                this.idAppointment,
                this.appointmentDateTime,
                patientShortDTO
        );
    }

}
