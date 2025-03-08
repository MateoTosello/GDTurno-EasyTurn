package com.backend.easyturn.repositories;

import com.backend.easyturn.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Administrator findByMail(String mail);
}
