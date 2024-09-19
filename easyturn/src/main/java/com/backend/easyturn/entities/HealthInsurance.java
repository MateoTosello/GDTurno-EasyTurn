package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHealthInsurance;

    @Column(nullable = false)
    private int healthInsuranceNumber;

    @Column(nullable = false)
    private String healthInsuranceName;

    @Column(nullable = false)
    private String healthInsurancePlan;

    @Column(nullable = false)
    private String healthInsuranceExpirationDate;

    @OneToMany(mappedBy = "healthInsurance", fetch = FetchType.LAZY) // NO VA CASCADE
    private Set<Patient> patients;


    public HealthInsuranceDTO toDTO() {
        HealthInsuranceDTO dto = new HealthInsuranceDTO();
        dto.setId(this.idHealthInsurance);
        dto.setHealthInsuranceNumber(this.healthInsuranceNumber);
        dto.setHealthInsuranceName(this.healthInsuranceName);
        dto.setHealthInsurancePlan(this.healthInsurancePlan);
        dto.setHealthInsuranceExpirationDate(this.healthInsuranceExpirationDate);

        if(!this.patients.isEmpty()) {
            dto.setPatients(this.patients.stream()
                    .map(patient -> new PatientShortDTO(
                            patient.getIdPatient(),
                            patient.getFirstName(),
                            patient.getLastName()))
                    .toList());
        }

        return dto;
    }
}