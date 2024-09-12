package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.AdministratorDTO;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public AdministratorDTO createAdministrator(Administrator administrator){
        try{
            Administrator adminFound = this.administratorRepository.findByMail(administrator.getMail());
            if(adminFound != null){
                throw new AppException("Este mail ya existe!", HttpStatus.CONFLICT);
            }
            Administrator adminCreated = administratorRepository.save(administrator);
            AdministratorDTO adminDTO = new AdministratorDTO(adminCreated.getId(), adminCreated.getMail(), adminCreated.getName(), adminCreated.getLastName());
            return adminDTO;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public void deleteAdministrator(int idAdministrator) {
        try {
            if(!this.administratorRepository.existsById(idAdministrator)){
                throw new AppException("El administrador no existe!", HttpStatus.NOT_FOUND);
            }
            this.administratorRepository.deleteById(idAdministrator);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public AdministratorDTO getAdministrator(int idAdministrator) {
        try{
            Administrator administrator = this.administratorRepository.findById(idAdministrator)
                    .orElseThrow(() -> new AppException("El administrador no existe!", HttpStatus.NOT_FOUND));
            AdministratorDTO administratorDTO = new AdministratorDTO(administrator.getId(), administrator.getMail(), administrator.getName(), administrator.getLastName());
            return administratorDTO;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public List<AdministratorDTO> getAllAdministrator() {
        try{
            List<AdministratorDTO> administratorDTOS = new ArrayList<>();
            List<Administrator> administrators = this.administratorRepository.findAll();
            if(administrators.isEmpty()){
                throw new AppException("No hay administradores cargados!", HttpStatus.NOT_FOUND);
            }
            for(Administrator admin:administrators){
                AdministratorDTO adminDTO = new AdministratorDTO(admin.getId(), admin.getMail(), admin.getName(), admin.getLastName());
                administratorDTOS.add(adminDTO);
            }
            return administratorDTOS;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public AdministratorDTO updateAdministrator (Administrator administrator) {
        try{
            Administrator adminFound = this.administratorRepository.findByMail(administrator.getMail());
            if(adminFound != null){
                throw new AppException("Este mail ya existe!", HttpStatus.CONFLICT);
            }
            Administrator admin = this.administratorRepository.findById(administrator.getId())
                    .orElseThrow(() -> new AppException("El administrador no existe!", HttpStatus.NOT_FOUND));
            admin.setMail(administrator.getMail());
            admin.setPassword(administrator.getPassword());
            Administrator administratorModified = this.administratorRepository.save(admin);
            AdministratorDTO administratorDTO = new AdministratorDTO(administratorModified.getId(), administratorModified.getMail(), administratorModified.getName(), administratorModified.getLastName());
            return administratorDTO;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
