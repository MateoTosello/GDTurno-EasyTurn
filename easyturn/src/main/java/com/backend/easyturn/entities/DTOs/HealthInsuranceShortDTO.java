package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthInsuranceShortDTO {
    private int idHealthInsurance;
    private String healthInsuranceName;
    private String healthInsurancePlan;

    public HealthInsuranceShortDTO(int idHealthInsurance, String healthInsuranceName, String healthInsurancePlan) {
        this.idHealthInsurance = idHealthInsurance;
        this.healthInsuranceName = healthInsuranceName;
        this.healthInsurancePlan = healthInsurancePlan;
    }
}