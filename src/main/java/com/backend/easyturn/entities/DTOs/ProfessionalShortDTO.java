package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfessionalShortDTO {
    private int idProfessional;
    private String professionalName;

    public ProfessionalShortDTO(int idProfessional, String professionalName) {
        this.idProfessional = idProfessional;
        this.professionalName = professionalName;
    }
}
