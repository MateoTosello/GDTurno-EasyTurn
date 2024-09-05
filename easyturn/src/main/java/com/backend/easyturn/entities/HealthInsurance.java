package com.backend.easyturn.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class HealthInsurance {

    @Id
    @Column(nullable = false)
    private int healthInsuranceNumber;

    @Column(nullable = false)
    private String healthInsurancePlan;

    @Column(nullable = false)
    private String healthInsuranceName;

    @Column(nullable = false)
    private String healthInsuranceExpirationDate;

    @ManyToMany(mappedBy = "")
    private Set<Patient> patients;

//    @ManyToMany(mappedBy = "specialities")
//    private Set<Professional> professionals;

}
