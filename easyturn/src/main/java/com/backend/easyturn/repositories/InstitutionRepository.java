package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Institution findByName(String institutionName);
}
