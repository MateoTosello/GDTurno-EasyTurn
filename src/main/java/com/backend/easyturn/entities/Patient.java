package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.HealthInsuranceShortDTO;
import com.backend.easyturn.entities.DTOs.PatientDTO;
import com.backend.easyturn.entities.DTOs.PatientShortDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Getter
@Setter
@Entity
public class Patient implements UserDetails {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPatient;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique=true)
    private int idCardNumber;

    @Column(nullable = false, unique=true)
    private String mail;

    @Column()
    private String password;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)  //Las operaciones CRUD se propagan a los turnos asociados. Al modificar un paciente, se modifican los turnos. Al eliminarlo, tambien se eliminan los turnos
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)   //No va el cascade. Cualquier operacion sobre el paciente (incluido delete) se propaga al healthInsurance
    @JoinColumn(name = "idHealthInsurance", nullable = false)
    private HealthInsurance healthInsurance;

    public PatientDTO toDTO() {
        PatientDTO dto = new PatientDTO();
        dto.setIdPatient(this.idPatient);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setIdCardNumber(this.idCardNumber);
        dto.setMail(this.mail);
        dto.setGender(this.gender);
        dto.setPhoneNumber(this.phoneNumber);
        dto.setBirthDate(this.birthDate);
        if (this.healthInsurance != null) {
            HealthInsuranceShortDTO healthInsuranceDTO = new HealthInsuranceShortDTO(
                    this.healthInsurance.getIdHealthInsurance(),
                    this.healthInsurance.getName(),
                    this.healthInsurance.getHealthInsurancePlan()
            );
            dto.setHealthInsurance(healthInsuranceDTO);
        }
        return dto;
    }
    public PatientShortDTO toShortDTO() {
        return new PatientShortDTO(
                this.idPatient,
                this.firstName,
                this.lastName
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public String getUsername() {
        return this.mail;
    }
}