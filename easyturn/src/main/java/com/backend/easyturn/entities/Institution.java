package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.*;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institution")
    private int idInstitution;

    @Column(name = "institution_name")
    private String name;

    @Column(name = "institution_address")
    private String institutionAddress;

    @Column(name = "institution_address_number")
    private String institutionAddressNumber;

    @ManyToMany(mappedBy = "institutions")
    Set<Professional> professionals = new HashSet<>();


    public InstitutionDTO toDTO() {
        InstitutionDTO dto = new InstitutionDTO();
        dto.setId(this.idInstitution);
        dto.setName(this.name);
        dto.setInstitutionAddress(this.institutionAddress);
        dto.setInstitutionAddressNumber(this.institutionAddressNumber);
        dto.setProfessionals(this.professionals.stream()
                .map(professional -> new ProfessionalShortDTO(
                        professional.getIdProfessional(),
                        professional.getProfessionalName()
                ))
                .toList());
        return dto;
    }
}
