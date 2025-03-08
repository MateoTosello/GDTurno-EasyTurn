package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InstitutionDTO {
    private int id;
    private String name;
    private String institutionAddress;
    private String institutionAddressNumber;
    private List<ProfessionalShortDTO> professionals;
}
