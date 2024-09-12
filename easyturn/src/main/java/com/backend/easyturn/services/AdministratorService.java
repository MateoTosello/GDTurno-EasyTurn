package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.AdministratorDTO;
import com.backend.easyturn.repositories.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public AdministratorDTO createAdministrator(Administrator administrator){
        try{
            Administrator adminCreated = administratorRepository.save(administrator);
            AdministratorDTO adminDTO = new AdministratorDTO(adminCreated.getId(), adminCreated.getMail(), adminCreated.getName(), adminCreated.getLastName());
            return adminDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteAdministrator(int idAdministrator) {
        try {
            this.administratorRepository.deleteById(idAdministrator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AdministratorDTO getAdministrator(int idAdministrator) {
        try{
            Administrator administrator = this.administratorRepository.findById(idAdministrator).get();
            AdministratorDTO administratorDTO = new AdministratorDTO(administrator.getId(), administrator.getMail(), administrator.getName(), administrator.getLastName());
            return administratorDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AdministratorDTO> getAllAdministrator() {
        try{
            List<AdministratorDTO> administratorDTOS=null;
            List<Administrator> administrators = this.administratorRepository.findAll();
            if(administrators.isEmpty()){
                return null;
            }
            for(Administrator admin:administrators){
                AdministratorDTO adminDTO = new AdministratorDTO(admin.getId(), admin.getMail(), admin.getName(), admin.getLastName());
                administratorDTOS.add(adminDTO);
            }
            return administratorDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AdministratorDTO updateAdministrator (Administrator administrator) {
        try{
            Administrator admin = this.administratorRepository.findById(administrator.getId()).get();
            admin.setMail(administrator.getMail());
            admin.setPassword(administrator.getPassword());
            Administrator administratorModified = this.administratorRepository.save(admin);
            AdministratorDTO administratorDTO = new AdministratorDTO(administratorModified.getId(), administratorModified.getMail(), administratorModified.getName(), administratorModified.getLastName());
            return administratorDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
