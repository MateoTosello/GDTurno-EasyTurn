package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPatient")
    private Patient patients;

//    @ManyToMany(mappedBy = "specialities")
//    private Set<Professional> professionals;
}
