package com.backend.easyturn.services;
import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.UserAuthenticationDTO;
import com.backend.easyturn.entities.DTOs.UserLoguedDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.RequestIncorrectLogin;
import com.backend.easyturn.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserLoguedDTO login(LoginRequest userLogin) {
        try{
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());
            try{
                this.authenticationManager.authenticate(userAuth);
            } catch (Exception e) {
               if(e instanceof AuthenticationException){
                     throw new RequestIncorrectLogin("Usuario o contraseña incorrectos");
               }
               throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            UserAuthenticationDTO user;
            if(userLogin.getRole().equals("ADMIN")){
                Administrator admin = this.administratorService.getByMail(userLogin.getEmail());
                user = new UserAuthenticationDTO(admin.getId(),admin.getMail(),"ADMIN",admin.getName()+" "+admin.getLastName());
            } else if (userLogin.getRole().equals("PROFESSIONAL")) {
                Professional professional = this.professionalService.getByEmail(userLogin.getEmail());
                user = new UserAuthenticationDTO(professional.getIdProfessional(),professional.getMail(),"PROFESSIONAL",professional.getProfessionalName());
            } else {
                Patient patient = this.patientService.getByEmail(userLogin.getEmail());
                user = new UserAuthenticationDTO(patient.getIdPatient(),patient.getMail(),"PATIENT",patient.getFirstName()+" "+patient.getLastName());
            }
            String token = this.jwtService.generateToken(user);
            return new UserLoguedDTO(token);
        }
        catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public UserLoguedDTO signIn(Patient userRegister) {
        try{
            userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            Patient newPatient = this.patientService.createPatient(userRegister,userRegister.getHealthInsurance().getIdHealthInsurance());
            UserAuthenticationDTO user = new UserAuthenticationDTO(newPatient.getIdPatient(),newPatient.getMail(),"PATIENT",newPatient.getFirstName()+" "+newPatient.getLastName());
            String token = this.jwtService.generateToken(user);
            return new UserLoguedDTO(token);
        }
        catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

}
