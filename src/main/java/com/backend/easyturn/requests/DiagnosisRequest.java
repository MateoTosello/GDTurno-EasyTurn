package com.backend.easyturn.requests;

import com.backend.easyturn.entities.Diagnosis;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisRequest {
    private Integer appointmentId;
    private Diagnosis diagnosis;
}
