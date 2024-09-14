package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.DTOs.InstitutionDTO;
import com.backend.easyturn.entities.Institution;
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

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody Institution institution) {
        Institution institutionCreated = this.institutionService.createInstitution(institution);
        return new ResponseEntity<>(institutionCreated.toDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/get-institution/{id}")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> getInstitution(@PathVariable int id) {
        Institution institution = this.institutionService.getInstitution(id);
        return new ResponseEntity<>(institution.toDTO(), HttpStatus.OK);
    }

    @GetMapping("/get-all-institutions")
    @ResponseBody
    public ResponseEntity<List<InstitutionDTO>> getAll(){
        List<Institution> institutions = this.institutionService.getAllInstitutions();
        List<InstitutionDTO> institutionsDTO = institutions.stream()
                .map(Institution::toDTO)
                .toList();
        return new ResponseEntity<>(institutionsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete-institution/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteInstitution(@PathVariable int id) {
        this.institutionService.deleteInstitution(id);
        return new ResponseEntity<>("Institucion eliminada correctamente",HttpStatus.OK);
    }

    @PutMapping("/update-institution")
    @ResponseBody
    public ResponseEntity<InstitutionDTO> updateInstitution(@RequestBody Institution institution) {
        Institution institutionUpdated = this.institutionService.updateInstitution(institution);
        return new ResponseEntity<>(institutionUpdated.toDTO(), HttpStatus.OK);
    }
}
