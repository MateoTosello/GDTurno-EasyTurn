package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DiagnosisDTO {
    private int diagnosisId;
    private String diagnosisDescription;
    private AppointmentShortDTO appointment;
}