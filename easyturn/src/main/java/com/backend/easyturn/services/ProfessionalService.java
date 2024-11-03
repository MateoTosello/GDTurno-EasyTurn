package com.backend.easyturn.services;

import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.exceptions.RequestIncompleteException;
import com.backend.easyturn.repositories.InstitutionRepository;
import com.backend.easyturn.repositories.ProfessionalRepository;
import com.backend.easyturn.repositories.SpecialityRepository;

import com.backend.easyturn.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private InstitutionRepository institutionRepository;

    public Professional createProfessional(Professional professional, List<Long> specialitiesIds, List<Integer> institutionsIds) {
        try {
            // Validar que el profesional no sea nulo
            if (professional == null) {
                throw new NotFoundException("El profesional no puede ser nulo");
            }

            Professional prof = this.professionalRepository.findByProfessionalRegistration(professional.getProfessionalRegistration());
            if (prof != null) {
                throw new IfClassExistsException("El profesional ya se encuentra registrado");
            }

            // Validar que specialitiesIds no esté vacío
            if (specialitiesIds == null || specialitiesIds.isEmpty()) {
                throw new RequestIncompleteException("Debe proporcionar al menos una especialidad");
            }

            // Validar que institutionsIds no esté vacío
            if (institutionsIds == null || institutionsIds.isEmpty()) {
                throw new RequestIncompleteException("Debe proporcionar al menos una institución");
            }

            Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(specialitiesIds));
            if (specialities.size() != specialitiesIds.size()) {
                throw new AppException("Una o más especialidades no encontradas", HttpStatus.NOT_FOUND);
            }
            professional.setSpecialities(specialities);

            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(institutionsIds));
            if (institutions.size() != institutionsIds.size()) {
                throw new NotFoundException("Una o más instituciones no encontradas");
            }
            professional.setInstitutions(institutions);

            return this.professionalRepository.save(professional);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Professional getProfessional(int id) {
        try {
            return this.professionalRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profesional no encontrado"));
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public List<Professional> getAllProfessionals() {
        try {
            List<Professional> professionals = this.professionalRepository.findAll();
            if (professionals.isEmpty()) {
                throw new NotFoundException("No existen profesionales");
            }
            return professionals;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public void deleteProfessional(int id) {
        try {
            Professional professional = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profesional no encontrado"));
            this.professionalRepository.delete(professional);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Professional updateProfessional(Professional professional) {
        try {
            Professional prof = this.professionalRepository.findById(professional.getIdProfessional())
                    .orElseThrow(() -> new NotFoundException("Profesional no encontrado"));
            prof.setProfessionalRegistration(professional.getProfessionalRegistration());
            prof.setProfessionalName(professional.getProfessionalName());
            return this.professionalRepository.save(prof);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }

    }

    public Professional addSpecialities(int id, List<Long> specialitiesIds) {
        try {
            if (specialitiesIds.isEmpty()) {
                throw new RequestIncompleteException("Lista de especialidades vacía");
            }

            Professional prof = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profesional no encontrado"));

            Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(specialitiesIds));
            if (specialities.size() != specialitiesIds.size()) {
                throw new NotFoundException("Una o mas especialidades no encontradas");
            }
            prof.getSpecialities().addAll(specialities);

            return this.professionalRepository.save(prof);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Professional addInstitutions(int id, List<Integer> institutionsIds) {
        try {
            if (institutionsIds.isEmpty()) {
                throw new RequestIncompleteException("Lista de instituciones vacía");
            }

            Professional prof = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profesional no encontrado"));

            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(institutionsIds));
            if (institutions.size() != institutionsIds.size()) {
                throw new NotFoundException("Una o mas instituciones no encontradas");
            }
            prof.getInstitutions().addAll(institutions);

            return this.professionalRepository.save(prof);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }
}