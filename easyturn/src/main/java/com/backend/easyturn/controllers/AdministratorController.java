package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.DTOs.AdministratorDTO;
import com.backend.easyturn.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;

    @PostMapping(path="/post")
    @ResponseBody
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody Administrator administrator) {
        AdministratorDTO administratorDTO = this.administratorService.createAdministrator(administrator);
        return new ResponseEntity<>(administratorDTO, HttpStatus.CREATED);
    }

    @GetMapping(path="/get-administrators")
    @ResponseBody
    public ResponseEntity<List<AdministratorDTO>> getAdministrators(){
        List<AdministratorDTO> administrators = this.administratorService.getAllAdministrator();
        return new ResponseEntity<>(administrators, HttpStatus.OK);
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteAdministrator(@PathVariable int id) {
        this.administratorService.deleteAdministrator(id);
        return new ResponseEntity<>("Administrador eliminado correctamente",HttpStatus.OK);
    }

    @GetMapping(path = "/get-administrator/{idAdmin}")
    @ResponseBody
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable int idAdmin){
        AdministratorDTO administrator = this.administratorService.getAdministrator(idAdmin);
        return new ResponseEntity<>(administrator, HttpStatus.OK);
    }
    
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<AdministratorDTO> updateAdministrator(@RequestBody Administrator administrator) {
        AdministratorDTO administratorDTO = this.administratorService.updateAdministrator(administrator);
        return new ResponseEntity<>(administratorDTO, HttpStatus.OK);
    }
}
