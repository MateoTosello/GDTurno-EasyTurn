package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.InstitutionShortDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.DTOs.SpecialityShortDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Professional {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfessional;

    @Column(unique = true, nullable = false)
    private String professionalRegistration; //Matricula del profesional

    @Column()
    private String professionalName;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professional", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "professional_specialities",
            joinColumns = @JoinColumn(name = "idProfessional"),
            inverseJoinColumns = @JoinColumn(name = "idSpeciality")
    )
    private Set<Speciality> specialities = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "institution_professional",
            joinColumns = @JoinColumn(name = "idProfessional"),
            inverseJoinColumns = @JoinColumn(name = "idInstitution")
    )
    private Set<Institution> institutions = new HashSet<>();


    public ProfessionalDTO toDTO() {
        ProfessionalDTO dto = new ProfessionalDTO();
        dto.setId(this.idProfessional);
        dto.setProfessionalName(this.professionalName);
        dto.setProfessionalRegistration(this.professionalRegistration);
        dto.setMail(this.mail);
        dto.setSpecialities(this.specialities.stream()
                .map(speciality -> new SpecialityShortDTO(
                        speciality.getIdSpeciality(),
                        speciality.getSpecialityName()))
                .toList());
        dto.setInstitutions(this.institutions.stream()
                .map(institution -> new InstitutionShortDTO(
                        institution.getIdInstitution(),
                        institution.getInstitutionName()))
                .toList());
        return dto;
    }

}
