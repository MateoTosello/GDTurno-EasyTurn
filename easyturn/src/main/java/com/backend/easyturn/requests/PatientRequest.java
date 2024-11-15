package com.backend.easyturn.requests;

import com.backend.easyturn.entities.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {
    private Patient patient;
    private int idHealthInsurance;
}