package com.backend.easyturn.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthenticationDTO {
    private int id;
    private String mail;
    private String role;
    private String fullName;
}
