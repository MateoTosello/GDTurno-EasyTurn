package com.backend.easyturn.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
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

    @ManyToMany(mappedBy = "")
    private Set<Patient> patients;

//    @ManyToMany(mappedBy = "specialities")
//    private Set<Professional> professionals;


    public int getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNumber(int healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    public String getHealthInsurancePlan() {
        return healthInsurancePlan;
    }

    public void setHealthInsurancePlan(String healthInsurancePlan) {
        this.healthInsurancePlan = healthInsurancePlan;
    }

    public String getHealthInsuranceName() {
        return healthInsuranceName;
    }

    public void setHealthInsuranceName(String healthInsuranceName) {
        this.healthInsuranceName = healthInsuranceName;
    }

    public String getHealthInsuranceExpirationDate() {
        return healthInsuranceExpirationDate;
    }

    public void setHealthInsuranceExpirationDate(String healthInsuranceExpirationDate) {
        this.healthInsuranceExpirationDate = healthInsuranceExpirationDate;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
