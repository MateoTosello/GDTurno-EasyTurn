package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class InstitutionShortDTO {
    private int idInstitution;
    private String institutionName;


    public InstitutionShortDTO(int idInstitution, String institutionName) {
        this.idInstitution = idInstitution;
        this.institutionName = institutionName;

    }
}