package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.requests.ProfessionalRequest;
import com.backend.easyturn.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {

    @Autowired
    private SpecialityService specialityService;

    @PostMapping("/create-speciality")
    @ResponseBody
    public ResponseEntity<Speciality> createSpeciality(@RequestBody Speciality speciality) {
        Speciality specialityCreated = this.specialityService.createSpeciality(speciality);
        return new ResponseEntity<>(specialityCreated, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-specialities")
    @ResponseBody
    public ResponseEntity<List<Speciality>> getAll() {
        List<Speciality> specialities = this.specialityService.getAllSpecialities();
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }

    @GetMapping("/get-speciality/{idSpeciality}")
    @ResponseBody
    public ResponseEntity<Speciality> getSpeciality(@PathVariable Long idSpeciality){
        Speciality speciality = specialityService.getSpecialityById(idSpeciality);
        return new ResponseEntity<>(speciality, HttpStatus.OK);
    }

    @DeleteMapping("/delete-speciality/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProfessional(@PathVariable long id) {
        this.specialityService.deleteSpecialityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-speciality")
    @ResponseBody
    public ResponseEntity<Speciality> updateSpeciality(@RequestBody Speciality speciality) {
        Speciality specialityUpdated = this.specialityService.updateSpeciality(speciality);
        return new ResponseEntity<>(specialityUpdated, HttpStatus.OK);
    }

}
