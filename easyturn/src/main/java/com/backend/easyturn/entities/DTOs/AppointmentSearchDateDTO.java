package com.backend.easyturn.entities.DTOs;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentSearchDateDTO {
    private Integer idProfessional;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}