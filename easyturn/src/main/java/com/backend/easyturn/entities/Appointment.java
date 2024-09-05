package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"appointmentDate", "appointmentTime","patientEmail","professionalRegistration"})
        }
)
public class Appointment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;

    @Column
    private LocalDate appointmentDate;

    @Column
    private LocalTime appointmentTime;

    @Column
    private String appointmentStatus;

    @Column
    private String patientValoration;

    @Column(name = "patient_email", insertable = false, updatable = false)
    private String patientEmail;

    @Column(name = "professional_registration", insertable = false, updatable = false)
    private String professionalRegistration;

    @ManyToOne
    @JoinColumn(name = "idPatient", referencedColumnName = "idPatient", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "idProfessional", referencedColumnName = "idProfessional", nullable = false)
    private Professional professional;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDiagnosis")
    private Diagnosis diagnosis;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSpeciality")
    private Speciality speciality;

}
