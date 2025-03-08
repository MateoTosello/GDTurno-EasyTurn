package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.AdministratorDTO;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.repositories.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public AdministratorDTO createAdministrator(Administrator administrator){
        try{
            Administrator adminFound = this.administratorRepository.findByMail(administrator.getMail());
            if(adminFound != null){
                throw new IfClassExistsException("Este mail ya existe!");
            }
            Administrator adminCreated = administratorRepository.save(administrator);
            AdministratorDTO adminDTO = new AdministratorDTO(adminCreated.getId(), adminCreated.getMail(), adminCreated.getName(), adminCreated.getLastName());
            return adminDTO;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Administrator getByMail(String mail){
        try{
            Administrator admin = this.administratorRepository.findByMail(mail);
            if (admin != null) {
                return admin;
            }
            throw new NotFoundException("No se encuentra ningun administrador con el mail: " + mail);
        }
        catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void deleteAdministrator(int idAdministrator) {
        try {
            if(!this.administratorRepository.existsById(idAdministrator)){
                throw new NotFoundException("El administrador no existe!");
            }
            this.administratorRepository.deleteById(idAdministrator);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public AdministratorDTO getAdministrator(int idAdministrator) {
        try{
            Administrator administrator = this.administratorRepository.findById(idAdministrator)
                    .orElseThrow(() -> new NotFoundException("El administrador no existe!"));
            AdministratorDTO administratorDTO = new AdministratorDTO(administrator.getId(), administrator.getMail(), administrator.getName(), administrator.getLastName());
            return administratorDTO;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public List<AdministratorDTO> getAllAdministrator() {
        try{
            List<AdministratorDTO> administratorDTOS = new ArrayList<>();
            List<Administrator> administrators = this.administratorRepository.findAll();
            if(administrators.isEmpty()){
                throw new NotFoundException("No hay administradores cargados!");
            }
            for(Administrator admin:administrators){
                AdministratorDTO adminDTO = new AdministratorDTO(admin.getId(), admin.getMail(), admin.getName(), admin.getLastName());
                administratorDTOS.add(adminDTO);
            }
            return administratorDTOS;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }
  
    public AdministratorDTO updateAdministrator (Administrator administrator) {
        try{
            Administrator adminFound = this.administratorRepository.findByMail(administrator.getMail());
            if(adminFound != null){
                throw new IfClassExistsException("Este mail ya existe!");
            }
            Administrator admin = this.administratorRepository.findById(administrator.getId())
                    .orElseThrow(() -> new NotFoundException("El administrador no existe!"));
            admin.setMail(administrator.getMail());
            admin.setPassword(administrator.getPassword());
            Administrator administratorModified = this.administratorRepository.save(admin);
            AdministratorDTO administratorDTO = new AdministratorDTO(administratorModified.getId(), administratorModified.getMail(), administratorModified.getName(), administratorModified.getLastName());
            return administratorDTO;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }
}
