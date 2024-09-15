package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthInsuranceShortDTO {
    private int idHealthInsurance;
    private String healthInsuranceName;

    public HealthInsuranceShortDTO(int idHealthInsurance, String healthInsuranceName) {
        this.idHealthInsurance = idHealthInsurance;
        this.healthInsuranceName = healthInsuranceName;
    }
}
