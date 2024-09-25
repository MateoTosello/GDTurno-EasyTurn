package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class HealthInsurance {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHealthInsurance;

    @Column(nullable = false)
    private int healthInsuranceNumber;

    @Column(nullable = false)
    private String healthInsurancePlan;

    @Column(nullable = false)
    private String healthInsuranceName;

    @Column(nullable = false)
    private String healthInsuranceExpirationDate;

    @Column(nullable = false)
    private Boolean active;

    @ManyToMany(mappedBy = "")
    private Set<Patient> patients;

}
