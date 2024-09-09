package com.backend.easyturn.services;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.Appointment;
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

    public int createAdministrator(Administrator administrator){
        Administrator adminCreated = administratorRepository.save(administrator);
        return adminCreated.getIdAdministrator();
    }
    public void deleteAdministrator(int idAdministrator) {
        this.administratorRepository.deleteById(idAdministrator);
    }
    public Administrator getAdministrator(int idAdministrator) {
        return this.administratorRepository.findById(idAdministrator).get();
    }
    public List<Administrator> getAllAdministrator() {
        return this.administratorRepository.findAll();
    }
    public Administrator updateAdministrator (Administrator administrator) {
        return this.administratorRepository.save(administrator);
    } //creo que reemplaza el existente por id --> ver porque no estoy seguro (otra opcion es traerlo por id, setear los datos nuevos y volverlo a guardar)
}
