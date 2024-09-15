package com.backend.easyturn.entities.DTOs;

import com.backend.easyturn.entities.HealthInsurance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private int idPateint;
    private String firstName;
    private String lastName;
    private String mail;
    private HealthInsuranceShortDTO healthInsurance;
}
