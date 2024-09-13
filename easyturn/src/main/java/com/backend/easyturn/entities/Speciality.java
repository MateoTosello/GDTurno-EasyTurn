package com.backend.easyturn.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Set<Professional> professionals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

}
