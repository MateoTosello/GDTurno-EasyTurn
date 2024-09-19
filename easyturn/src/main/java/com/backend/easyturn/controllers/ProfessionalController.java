package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.requests.ProfessionalRequest;
import com.backend.easyturn.services.ProfessionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> createProfessional(@RequestBody ProfessionalRequest request) {
        Professional professionalCreated = this.professionalService.createProfessional(request.getProfessional(), request.getSpecialitiesIds(), request.getInstitutionsIds());
        return new ResponseEntity<>(professionalCreated.toDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/get-professional/{id}")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> getProfessional(@PathVariable int id) {
        Professional professional = this.professionalService.getProfessional(id);
        return new ResponseEntity<>(professional.toDTO(), HttpStatus.OK);
    }

    @GetMapping("/get-all-professionals")
    @ResponseBody
    public ResponseEntity<List<ProfessionalDTO>> getAll() {
        List<Professional> professionals = this.professionalService.getAllProfessionals();
        List<ProfessionalDTO> professionalsDTO = professionals.stream()
                .map(Professional::toDTO)
                .toList();
        return new ResponseEntity<>(professionalsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete-professional/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProfessional(@PathVariable int id) {
        this.professionalService.deleteProfessional(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-professional")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> updateProfessional(@RequestBody Professional professional) {
        Professional professionalUpdated = this.professionalService.updateProfessional(professional);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

    @PostMapping("add-specialities/{id}")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> addSpeciality(@PathVariable int id, @RequestBody List<Long> specialitiesIds){
        Professional professionalUpdated = this.professionalService.addSpecialities(id,specialitiesIds);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

    @PostMapping("add-institutions/{id}")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> addInstitution(@PathVariable int id, @RequestBody List<Integer> institutionsIds){
        Professional professionalUpdated = this.professionalService.addInstitutions(id,institutionsIds);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

} 
