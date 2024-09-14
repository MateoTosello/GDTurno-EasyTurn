package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @Column(unique = true)
    private String mail;

    @Column()
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professional", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @ManyToMany(mappedBy = "professionals")
    Set<Institution> institutions = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "professional_specialities",
            joinColumns = @JoinColumn(name = "idProfessional"),
            inverseJoinColumns = @JoinColumn(name = "idSpeciality")
    )

    private Set<Speciality> specialities = new HashSet<>();


}
