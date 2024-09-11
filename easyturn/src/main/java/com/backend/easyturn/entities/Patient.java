package com.backend.easyturn.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Patient {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPatient;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique=true)
    private int IDCardNumber;

    @Column(name = "patient_email", nullable = false, unique=true)
    private String patientEmail;

    @Column(nullable = false)
    private int birthDate;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @ManyToMany()
    @JoinTable(
            name = "patient_healthInsurance",
            joinColumns = @JoinColumn(name = "idPatient"),
            inverseJoinColumns = @JoinColumn(name = "numberHealthInsurance")

    )
    private Set<HealthInsurance> healthInsurances;

}
