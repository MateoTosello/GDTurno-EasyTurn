package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Professional {

    @Id
    @Column(name = "id_professional")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfessional;

    @Column(name = "professional_registration", unique = true)
    private String professionalRegistration; //Matricula del profesional

    @Column(name = "profeesional_name")
    private String professionalName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professional", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @ManyToMany(mappedBy = "professionals")
    Set<Institution> institutions;

    @ManyToMany()
    @JoinTable(
            name = "professional_specialities",
            joinColumns = @JoinColumn(name = "id_professional"),
            inverseJoinColumns = @JoinColumn(name = "idSpeciality")
    )
    private Set<Speciality> specialities;

}
