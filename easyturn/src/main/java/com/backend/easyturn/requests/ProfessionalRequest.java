package com.backend.easyturn.requests;

import com.backend.easyturn.entities.Professional;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfessionalRequest {
    private Professional professional;
    private List<Long> specialitiesIds;

}
