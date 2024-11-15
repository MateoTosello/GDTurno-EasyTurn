package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.UserLoginDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.AdministratorRepository;
import com.backend.easyturn.repositories.PatientRepository;
import com.backend.easyturn.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
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

    public String login(UserLoginDTO userLoginDTO) {
        String token = "";
        int count = 0;
        try{
            Administrator administrator = this.administratorRepository.findByMail(userLoginDTO.getEmail());
            if(administrator != null) {
                count++;
                if(administrator.getPassword().equals(userLoginDTO.getPassword())) {
                    token="token como administrador";
                }
                else {
                    throw new AppException("Contraseña incorrecta", HttpStatus.CONFLICT);
                }
            }
            Professional professional = this.professionalRepository.findByMail(userLoginDTO.getEmail());
            if(professional != null){
                count++;
                if(professional.getPassword().equals(userLoginDTO.getPassword())) {
                    token= "token como profesional";
                }
                else {
                    throw new AppException("Contraseña incorrecta", HttpStatus.CONFLICT);
                }
            }
            Patient patient = this.patientRepository.findByMail(userLoginDTO.getEmail());
            if(patient != null){
                count++;
                if(patient.getPassword().equals(userLoginDTO.getPassword())) {
                    token= "token como paciente";
                }
                else {
                    throw new AppException("Contraseña incorrecta", HttpStatus.CONFLICT);
                }
            }
            if(count == 0) {
                throw new AppException("Usuario no encontrado", HttpStatus.CONFLICT);
            }
        }
        catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.CONFLICT);
        }
        return token;
    }
}
