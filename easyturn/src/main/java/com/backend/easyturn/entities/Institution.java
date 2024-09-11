package com.backend.easyturn.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Institution")
public class Institution {

    @Id
    @Column
    private int idInstitution;

    @Column
    private String institutionName;

    @Column
    private String institutionAddress;

    @Column
    private String institutionAddressNumber;

    @ManyToMany()
    @JoinTable(
            name = "institution_professional",
            joinColumns = @JoinColumn(name = "idInstitution"),
            inverseJoinColumns = @JoinColumn(name = "idProfessional")
    )
    private Set<Professional> professionals;
}
