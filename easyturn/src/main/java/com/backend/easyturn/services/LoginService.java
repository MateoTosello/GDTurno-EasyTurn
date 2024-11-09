package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.UserLoginDTO;
import com.backend.easyturn.entities.DTOs.UserLoguedDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.exceptions.RequestIncorrectLogin;
import com.backend.easyturn.repositories.AdministratorRepository;
import com.backend.easyturn.repositories.PatientRepository;
import com.backend.easyturn.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private PatientRepository patientRepository;

    public UserLoguedDTO login(UserLoginDTO userLoginDTO) {
        try {
            Administrator administrator = this.administratorRepository.findByMail(userLoginDTO.getEmail());
            if (administrator != null) {

                if (administrator.getPassword().equals(userLoginDTO.getPassword())) {
                    UserLoguedDTO userLogued = new UserLoguedDTO("token como administrador", administrator.getName(), "administrator", administrator.getId());
                    return userLogued;
                } else {
                    throw new RequestIncorrectLogin("Contraseña incorrecta");
                }
            }
            Professional professional = this.professionalRepository.findByMail(userLoginDTO.getEmail());
            if (professional != null) {

                if (professional.getPassword().equals(userLoginDTO.getPassword())) {
                    UserLoguedDTO userLogued = new UserLoguedDTO("token como profesional", professional.getProfessionalName(), "professional", professional.getIdProfessional());
                    return userLogued;
                } else {
                    throw new RequestIncorrectLogin("Contraseña incorrecta");
                }
            }
            Patient patient = this.patientRepository.findByMail(userLoginDTO.getEmail());
            if (patient != null) {

                if (patient.getPassword().equals(userLoginDTO.getPassword())) {
                    UserLoguedDTO userLogued = new UserLoguedDTO("token como paciente", patient.getFirstName(), "patient", patient.getIdPatient());
                    return userLogued;
                } else {
                    throw new RequestIncorrectLogin("Contraseña incorrecta");
                }
            }
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
        throw new NotFoundException("Usuario no encontrado");
    }
}
