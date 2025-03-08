package com.backend.easyturn.entities.DTOs;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HealthInsuranceDTO {
    private int id;
    private int healthInsuranceNumber;
    private String name;
    private String healthInsurancePlan;
    private String healthInsuranceExpirationDate;
    private List<PatientShortDTO> patients;
}
