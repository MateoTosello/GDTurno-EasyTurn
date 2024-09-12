package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.Speciality;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createSpeciality(@RequestBody Speciality speciality) {
        this.specialityService.createSpeciality(speciality);
    }

    @GetMapping("/get-specialities")
    @ResponseBody
    public List<Speciality> getSpecialities(){
        return this.specialityService.findAllSpecialities();
    }

    @GetMapping("/get-speciality/{idSpeciality}")
    @ResponseBody
    public ResponseEntity<Speciality> getSpeciality(@PathVariable Long idSpeciality){
        Speciality speciality = specialityService.findSpecialityById(idSpeciality)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Speciality not found"));

        return ResponseEntity.ok(speciality);
    }

    @PostMapping("/delete-speciality/{idSpeciality}")
    public void deleteSpeciality(@PathVariable Long idSpeciality){
        this.specialityService.deleteSpecialityById(idSpeciality);
    }

}
