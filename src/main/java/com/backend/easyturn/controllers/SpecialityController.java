package com.backend.easyturn.controllers;



import com.backend.easyturn.entities.DTOs.SpecialityDTO;

import com.backend.easyturn.entities.Speciality;

import com.backend.easyturn.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {

    @Autowired
    private SpecialityService specialityService;

    @CrossOrigin(origins = "*")
    @PostMapping("/create-speciality")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<SpecialityDTO> createSpeciality(@RequestBody Speciality speciality) {
        Speciality specialityCreated = this.specialityService.createSpeciality(speciality);
        return new ResponseEntity<>(specialityCreated.toDTO(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-specialities")
    @ResponseBody
    public ResponseEntity<List<SpecialityDTO>> getAll() {
        List<Speciality> specialities = this.specialityService.getAllSpecialities();
        List<SpecialityDTO> specialitiesDTO = specialities.stream()
                .map(Speciality::toDTO)
                .toList();
        return new ResponseEntity<>(specialitiesDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-speciality/{idSpeciality}")
    @ResponseBody
    public ResponseEntity<SpecialityDTO> getSpeciality(@PathVariable Long idSpeciality){
        Speciality speciality = specialityService.getSpecialityById(idSpeciality);
        return new ResponseEntity<>(speciality.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete-speciality/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteProfessional(@PathVariable long id) {
        this.specialityService.deleteSpecialityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update-speciality")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<SpecialityDTO> updateSpeciality(@RequestBody Speciality speciality) {
        Speciality specialityUpdated = this.specialityService.updateSpeciality(speciality);
        return new ResponseEntity<>(specialityUpdated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/speciality-names")
    @ResponseBody
    public ResponseEntity<List<String>> getAllSpecialityNames() {
        List<String> specialityNames = this.specialityService.getAllSpecialityNames();
        return new ResponseEntity<>(specialityNames, HttpStatus.OK);
    }
}
