package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoguedDTO {
    private String token;

    public UserLoguedDTO(String token) {
        this.token = token;

    }
}

