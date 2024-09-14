package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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

    @Column(nullable = false, unique=true)
    private String mail;

    @Column()
    private String password;

    @Column(nullable = false)
    private int birthDate;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<HealthInsurance> healthInsurances;

}
