package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Institution> createInstitution(@RequestBody Institution institution) {
        Institution institutionCreadted = this.institutionService.createInstitution(institution);
        return new ResponseEntity<>(institutionCreadted, HttpStatus.CREATED);
    }

    @GetMapping("/get-institution/{id}")
    @ResponseBody
    public ResponseEntity<Institution> getInstitution(@PathVariable int id) {
        Institution institution = this.institutionService.getInstitution(id);
        return new ResponseEntity<>(institution, HttpStatus.OK);
    }

    @GetMapping("/get-all-institutions")
    @ResponseBody
    public ResponseEntity<List<Institution>> getAll(){
        List<Institution> institutions = this.institutionService.getAllInstitutions();
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }

    @DeleteMapping("/delete-institution/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable int id) {
        this.institutionService.deleteInstitution(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-institution")
    @ResponseBody
    public ResponseEntity<Institution> updateInstitution(@RequestBody Institution institution) {
        Institution institutionUpdated = this.institutionService.updateInstitution(institution);
        return new ResponseEntity<>(institutionUpdated, HttpStatus.OK);
    }
}
