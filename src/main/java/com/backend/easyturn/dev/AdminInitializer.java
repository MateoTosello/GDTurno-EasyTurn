package com.backend.easyturn.dev;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.repositories.AdministratorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner initAdministrator(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (administratorRepository.count() == 0) { // Evitar duplicados
                Administrator admin = new Administrator();
                admin.setMail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // Hashear la contraseña
                admin.setName("Admin");
                admin.setLastName("User");

                administratorRepository.save(admin);
            }
        };
    }
}
