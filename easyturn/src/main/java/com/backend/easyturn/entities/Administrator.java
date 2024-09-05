package com.backend.easyturn.entities;

import jakarta.persistence.*;

@Entity
public class Administrator {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdministrator;

    @Column(unique = true, nullable = false)
    private String mailAdministrator;

    @Column(nullable = false)
    private String password;
}
