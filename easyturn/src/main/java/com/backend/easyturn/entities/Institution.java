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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institution")
    private int idInstitution;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "institution_address")
    private String institutionAddress;

    @Column(name = "institution_address_number")
    private String institutionAddressNumber;

    @ManyToMany()
    @JoinTable(
            name = "institution_professional",
            joinColumns = @JoinColumn(name = "idInstitution"),
            inverseJoinColumns = @JoinColumn(name = "idProfessional")
    )
    private Set<Professional> professionals;
}
