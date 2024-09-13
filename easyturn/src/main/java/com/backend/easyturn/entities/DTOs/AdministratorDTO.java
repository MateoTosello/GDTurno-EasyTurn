package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDTO {
    private int id;
    private String mail;
    private String name;
    private String lastname;

    public AdministratorDTO(int id,String mail,String name,String lastname) {
        this.setId(id);
        this.setMail(mail);
        this.setName(name);
        this.setLastname(lastname);
    }
}
