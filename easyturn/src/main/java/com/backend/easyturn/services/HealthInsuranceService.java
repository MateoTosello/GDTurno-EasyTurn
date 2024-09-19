package com.backend.easyturn.services;


import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.HealthInsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthInsuranceService {

    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;

    public HealthInsurance createHealthInsurance (HealthInsurance healthInsurance) {
        try {
            // Validar que la os no sea nula
            if (healthInsurance == null) {
                throw new AppException("La Obra Social no puede ser nula", HttpStatus.BAD_REQUEST);
            }

            HealthInsurance healthIns = this.healthInsuranceRepository.findByHealthInsuranceNumber(healthInsurance.getHealthInsuranceNumber());
            if (healthIns != null) {
                throw new AppException("La Obra Social ya se encuentra registrada", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return this.healthInsuranceRepository.save(healthInsurance);

        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<HealthInsurance> getAllHealthInsurances() {
        try {
            List<HealthInsurance> healthInsurances = this.healthInsuranceRepository.findAll();
            if (healthInsurances.isEmpty()) {
                throw new AppException("No existen Obras Sociales", HttpStatus.NOT_FOUND);
            }
            return healthInsurances;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HealthInsurance getHealthInsurance(int id) {
        try {
            return this.healthInsuranceRepository.findById(id)
                    .orElseThrow(() -> new AppException("Obra Social no encontrada", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteHealthInsurance(int id){
        try {
            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(id)
                    .orElseThrow(()-> new AppException("Obra social no encontrada", HttpStatus.NOT_FOUND));

            if (!healthInsurance.getPatients().isEmpty())
                throw new AppException("No se puede eliminar la Obra Social porque tiene pacientes asociados", HttpStatus.CONFLICT);
            this.healthInsuranceRepository.delete(healthInsurance);

        } catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HealthInsurance updateHealthInsurance(HealthInsurance healthInsurance){
        try {
            HealthInsurance healthIns = healthInsuranceRepository.findById(healthInsurance.getIdHealthInsurance())
                    .orElseThrow(()-> new AppException("Obra Social no encontrada", HttpStatus.NOT_FOUND));
            healthIns.setHealthInsuranceNumber(healthInsurance.getHealthInsuranceNumber());
            healthIns.setHealthInsuranceName(healthInsurance.getHealthInsuranceName());
            healthIns.setHealthInsurancePlan(healthInsurance.getHealthInsurancePlan());
            healthIns.setHealthInsuranceExpirationDate(healthInsurance.getHealthInsuranceExpirationDate());

            return this.healthInsuranceRepository.save(healthIns);

        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}