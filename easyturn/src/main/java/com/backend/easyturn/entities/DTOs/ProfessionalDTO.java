package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfessionalDTO {
    private int id;
    private String professionalName;
    private String professionalRegistration;
    private String mail;
    private List<SpecialityShortDTO> specialities;
    private List<InstitutionShortDTO> institutions;

}
