package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.Administrator;
import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.services.HealthInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthinsurance")
public class HealthInsuranceController {

    @Autowired
    HealthInsuranceService healthInsuranceService;

    @GetMapping(value = "/get-all-healthinsuranse")
    public List<HealthInsurance> getHealthInsurance() {
        return this.healthInsuranceService.getAllHealthInsurances();
    }

    @GetMapping(value = "/get-healthinsuranse/{id}")
    public HealthInsurance getOneHealthInsurance(@PathVariable int id) {
        return this.healthInsuranceService.getOneHealthInsurance(id);
    }

    @PostMapping(value = "/post-healthinsurance")
    @ResponseBody
    public long createHealthInsurance(@RequestBody HealthInsurance healthInsurance) {
        return this.healthInsuranceService.createHealthInsurance(healthInsurance);
    };

    @DeleteMapping(value = "/delete-healthinsurance/{id}")
    public boolean deleteHealthInsurance(@PathVariable int id){
        return this.healthInsuranceService.deleteHealthInsurance(id);
    };

    @PutMapping(value = "/update-healthinsurance/{id}")
    public HealthInsurance updateHealthInsurance(@RequestBody HealthInsurance healthInsurance,@PathVariable int id){
        return this.healthInsuranceService.updateHealthInsurance(healthInsurance,id);
    }
}
