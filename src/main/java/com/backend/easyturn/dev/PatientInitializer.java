package com.backend.easyturn.dev;

import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.repositories.HealthInsuranceRepository;
import com.backend.easyturn.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

@Configuration
public class PatientInitializer {
    @Bean
    CommandLineRunner initPatient(PatientRepository patientRepository, HealthInsuranceRepository healthInsuranceRepository, PasswordEncoder passwordEncoder) {

        return args -> {
            if (patientRepository.count() == 0) { // Evitar duplicados
                Patient patient = new Patient();
                patient.setFirstName("John");
                patient.setLastName("Doe");
                patient.setMail("patient@gmail.com");
                patient.setPassword(passwordEncoder.encode("patient123")); // Hashear la contraseña
                patient.setPhoneNumber("1234567890");
                patient.setIdCardNumber(213123);
                patient.setGender("Masculino");
                patient.setBirthDate(new Date());

                HealthInsurance healthInsurance = new HealthInsurance();
                healthInsurance.setName("OSDE");
                healthInsurance.setActive(true);
                healthInsurance.setHealthInsuranceExpirationDate("2028-12-31");
                healthInsurance.setHealthInsuranceNumber(123);
                healthInsurance.setHealthInsurancePlan("Plan 1");
                HealthInsurance newHealth = healthInsuranceRepository.save(healthInsurance);
                patient.setHealthInsurance(newHealth);
                patientRepository.save(patient);
            }
    };
}
}
