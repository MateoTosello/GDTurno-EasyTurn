package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecialityShortDTO {
    private Long idSpeciality;
    private String specialityName;

    public SpecialityShortDTO(Long idSpeciality, String specialityName) {
        this.idSpeciality = idSpeciality;
        this.specialityName = specialityName;
    }
}
