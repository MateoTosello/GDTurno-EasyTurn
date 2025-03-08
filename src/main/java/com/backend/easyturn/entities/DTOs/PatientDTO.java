package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientDTO {
    private int idPatient;
    private String firstName;
    private String lastName;
    private int idCardNumber;
    private String mail;
    private String gender;
    private String phoneNumber;
    private Date birthDate;
    private HealthInsuranceShortDTO healthInsurance;
}