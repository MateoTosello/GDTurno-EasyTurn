package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SpecialityDTO {
    private Long id;
    private String specialityName;
    private String specialityDescription;
    private List<ProfessionalShortDTO> professionals;
}
