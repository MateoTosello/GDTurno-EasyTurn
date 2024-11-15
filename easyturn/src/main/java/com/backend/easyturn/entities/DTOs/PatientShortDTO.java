package com.backend.easyturn.entities.DTOs;

import com.backend.easyturn.entities.HealthInsurance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientShortDTO {
    private int idPatient;
    private String firstName;
    private String lastName;

    public PatientShortDTO(int idPatient, String firstName, String lastName) {
        this.idPatient = idPatient;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}