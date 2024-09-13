package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.services.ProfessionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/institution")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Professional> createProfessional(@RequestBody Professional professional) {
        Professional professionalCreated = this.professionalService.createProfessional(professional);
        return new ResponseEntity<>(professionalCreated, HttpStatus.CREATED);
    }

    @GetMapping("/get-professional/{id}")
    @ResponseBody
    public ResponseEntity<Professional> getProfessional(@PathVariable int id) {
        Professional professional = this.professionalService.getProfessional(id);
        return new ResponseEntity<>(professional, HttpStatus.OK);
    }

    @GetMapping("/get-all-professionals")
    @ResponseBody
    public ResponseEntity<List<Professional>> getAll() {
        List<Professional> professionals = this.professionalService.getAllProfessionals();
        return new ResponseEntity<>(professionals, HttpStatus.OK);
    }

    @DeleteMapping("/delete-professional/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProfessional(@PathVariable int id) {
        this.professionalService.deleteProfessional(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-professional")
    @ResponseBody
    public ResponseEntity<Professional> updateProfessional(@RequestBody Professional professional) {
        Professional professionalUpdated = this.professionalService.updateProfessional(professional);
        return new ResponseEntity<>(professionalUpdated, HttpStatus.OK);
    }


}
