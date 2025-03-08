package com.backend.easyturn.config;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.repositories.AdministratorRepository;
import com.backend.easyturn.repositories.PatientRepository;
import com.backend.easyturn.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final AdministratorRepository administratorRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Administrator admin = this.administratorRepository.findByMail(email);
            if (admin != null) {
                return admin;
            }
            Patient patient = this.patientRepository.findByMail(email);
            if (patient != null) {
                return patient;
            }
            Professional professional = this.professionalRepository.findByMail(email);
            if (professional != null) {
                return professional;
            }
            throw new UsernameNotFoundException("No se encontro el usuario con el email: " + email);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
