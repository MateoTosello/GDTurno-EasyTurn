package com.backend.easyturn.services;


import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.HealthInsurance;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.exceptions.RequestIncompleteException;
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
                throw new RequestIncompleteException("La Obra Social no puede ser nula");
            }

            HealthInsurance healthIns = this.healthInsuranceRepository.findByHealthInsuranceNumber(healthInsurance.getHealthInsuranceNumber());
            if (healthIns != null) {
                throw new IfClassExistsException("La Obra Social ya se encuentra registrada");
            }
            healthInsurance.setActive(true); // ALTA LOGICA POR DEFECTO
            return this.healthInsuranceRepository.save(healthInsurance);

        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public List<HealthInsurance> getAllHealthInsurances() throws AppException {
        try {
            List<HealthInsurance> healthInsurances = this.healthInsuranceRepository.findAll();
            if (healthInsurances.isEmpty()) {
                throw new NotFoundException("No existen Obras Sociales");
            }
            return healthInsurances;
        } catch (AppException e) {
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public HealthInsurance getHealthInsurance(int id) {
        try {
            return this.healthInsuranceRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Obra Social no encontrada"));
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public void deleteHealthInsurance(int id){
        try {
            HealthInsurance healthInsurance = this.healthInsuranceRepository.findById(id)
                    .orElseThrow(()-> new NotFoundException("Obra social no encontrada"));
            //ver esto, se deberia poder ... primero damos de baja los pacientes y luego la OS
            if (!healthInsurance.getPatients().isEmpty())
                throw new AppException("No se puede eliminar la Obra Social porque tiene pacientes asociados", HttpStatus.CONFLICT);

            // UTILIZAMOS BAJA LOGICA PARA MANTENER REGISTROS ANTERIORES
            healthInsurance.setActive(false);
            this.healthInsuranceRepository.save(healthInsurance);

        } catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public HealthInsurance updateHealthInsurance(HealthInsurance healthInsurance){
        try {
            HealthInsurance healthIns = healthInsuranceRepository.findById(healthInsurance.getIdHealthInsurance())
                    .orElseThrow(()-> new NotFoundException("Obra Social no encontrada"));
            healthIns.setHealthInsuranceNumber(healthInsurance.getHealthInsuranceNumber());
            healthIns.setName(healthInsurance.getName());
            healthIns.setHealthInsurancePlan(healthInsurance.getHealthInsurancePlan());
            healthIns.setHealthInsuranceExpirationDate(healthInsurance.getHealthInsuranceExpirationDate());

            return this.healthInsuranceRepository.save(healthIns);

        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

}