package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Professional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

    Professional findByProfessionalRegistration(String professionalRegistration);

}
