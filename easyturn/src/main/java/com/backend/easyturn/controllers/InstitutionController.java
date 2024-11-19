package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.DTOs.InstitutionDTO;
import com.backend.easyturn.entities.DTOs.SpecialityDTO;
import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @CrossOrigin(origins = "*")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody Institution institution) {
        Institution institutionCreated = this.institutionService.createInstitution(institution);
        return new ResponseEntity<>(institutionCreated.toDTO(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> getInstitution(@PathVariable int id){
        Institution institution = this.institutionService.getInstitution(id);
        return new ResponseEntity<>(institution.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<InstitutionDTO>> getAll(){
        List<Institution> institutions = this.institutionService.getAllInstitutions();
        List<InstitutionDTO> institutionsDTO = institutions.stream()
                .map(Institution::toDTO)
                .toList();
        return new ResponseEntity<>(institutionsDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteInstitution(@PathVariable int id) {
        this.institutionService.deleteInstitution(id);
        return new ResponseEntity<>("Institucion eliminada correctamente",HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> updateInstitution(@RequestBody Institution institution) {
        Institution institutionUpdated = this.institutionService.updateInstitution(institution);
        return new ResponseEntity<>(institutionUpdated.toDTO(), HttpStatus.OK);
    }
}
