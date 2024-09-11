package com.backend.easyturn.entities;

import jakarta.persistence.*;

@Entity
public class Diagnosis {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiagnosis;

    @Column
    private String diagnosisDescription;

    @OneToOne(mappedBy = "diagnosis")
    private Appointment appointment;
}
