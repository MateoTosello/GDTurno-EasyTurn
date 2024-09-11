package com.backend.easyturn.services;


import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.repositories.HealthInsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthInsuranceService {

    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;

    public int createHealthInsurance (HealthInsurance healthInsurance) {
        HealthInsurance hIn = this.healthInsuranceRepository.save(healthInsurance);
        return hIn.getHealthInsuranceNumber();
    }

    public List<HealthInsurance> getAllHealthInsurances () {
        return this.healthInsuranceRepository.findAll();
    }

    public HealthInsurance getOneHealthInsurance(int id){
        return this.healthInsuranceRepository.findById(id).get();
    }

    public boolean deleteHealthInsurance(int id){
        try {
            this.healthInsuranceRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public HealthInsurance updateHealthInsurance(HealthInsurance request, int id){
        HealthInsurance healthInsurance = healthInsuranceRepository.findById(id).get();
        healthInsurance.setHealthInsuranceExpirationDate(request.getHealthInsuranceExpirationDate());
        healthInsurance.setHealthInsurancePlan(request.getHealthInsurancePlan());
        healthInsurance.setPatients(request.getPatients());
        return healthInsurance;
    }


}
