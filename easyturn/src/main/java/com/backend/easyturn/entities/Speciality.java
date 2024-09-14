package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.DTOs.SpecialityDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSpeciality;

    @Column
    private String specialityName;

    @Column
    private String specialityDescription;

    @ManyToMany(mappedBy = "specialities")
    private Set<Professional> professionals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;


    public SpecialityDTO toDTO() {
        SpecialityDTO dto = new SpecialityDTO();
        dto.setId(this.idSpeciality);
        dto.setSpecialityName(this.specialityName);
        dto.setSpecialityDescription(this.specialityDescription);
        dto.setProfessionalNames(this.professionals.stream()
                .map(Professional::getProfessionalName)
                .toList());
        return dto;
    }
}
