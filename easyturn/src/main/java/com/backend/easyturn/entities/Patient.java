package com.backend.easyturn.entities;

import com.backend.easyturn.entities.DTOs.HealthInsuranceShortDTO;
import com.backend.easyturn.entities.DTOs.PatientDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Patient {

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

    @Column(nullable = false)
    private String phoneNumber;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idHealthInsurance", nullable = false)
    private HealthInsurance healthInsurance;

    public PatientDTO toDTO() {
        PatientDTO dto = new PatientDTO();
        dto.setIdPatient(this.idPatient);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setIdCardNumber(this.idCardNumber);
        dto.setMail(this.mail);
        if (this.healthInsurance != null) {
            HealthInsuranceShortDTO healthInsuranceDTO = new HealthInsuranceShortDTO(
                    this.healthInsurance.getIdHealthInsurance(),
                    this.healthInsurance.getHealthInsuranceName()
            );
            dto.setHealthInsurance(healthInsuranceDTO);
        }
        return dto;
    }
}