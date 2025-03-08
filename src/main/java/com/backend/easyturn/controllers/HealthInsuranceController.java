package com.backend.easyturn.controllers;


import com.backend.easyturn.GlobalExceptionHandler;
import com.backend.easyturn.entities.DTOs.HealthInsuranceDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.requests.ProfessionalRequest;
import com.backend.easyturn.services.HealthInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthinsurance")
public class HealthInsuranceController {

    @Autowired
    HealthInsuranceService healthInsuranceService;
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<List<HealthInsuranceDTO>> getAll() throws AppException {
        List<HealthInsurance> healthInsurances = this.healthInsuranceService.getAllHealthInsurances();
        List<HealthInsuranceDTO> healthInsurancesDTO = healthInsurances.stream()
                .map(HealthInsurance::toDTO)
                .toList();
        return new ResponseEntity<>(healthInsurancesDTO, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<HealthInsuranceDTO> getHealthInsurance(@PathVariable int id) {
        HealthInsurance healthInsurance = this.healthInsuranceService.getHealthInsurance(id);
        return new ResponseEntity<>(healthInsurance.toDTO(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<HealthInsurance> createHealthInsurance(@RequestBody HealthInsurance healthInsurance) {
        HealthInsurance healthInsuranceCreated = this.healthInsuranceService.createHealthInsurance(healthInsurance);
        return new ResponseEntity<>(healthInsuranceCreated, HttpStatus.CREATED);
    };

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete-healthinsurance/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHealthInsurance(@PathVariable int id){
        this.healthInsuranceService.deleteHealthInsurance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    };

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update-healthinsurance")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<HealthInsuranceDTO> updateHealthInsurance(@RequestBody HealthInsurance healthInsurance){
        HealthInsurance healthInsuranceUpdated = this.healthInsuranceService.updateHealthInsurance(healthInsurance);
        return new ResponseEntity<>(healthInsuranceUpdated.toDTO(), HttpStatus.OK);
    }

}