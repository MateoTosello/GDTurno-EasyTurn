package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Integer> {
}