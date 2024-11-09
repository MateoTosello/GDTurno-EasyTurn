package com.backend.easyturn.entities.DTOs;

public class UserLoguedDTO {
    private String token;
    private String username;
    private String role;
    private int id;

    public UserLoguedDTO(String token, String username, String role, int id) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.id = id;
    }
}

