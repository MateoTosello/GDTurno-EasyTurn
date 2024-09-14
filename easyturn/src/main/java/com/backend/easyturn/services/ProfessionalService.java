package com.backend.easyturn.services;

import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
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
        try{
            Professional prof = this.professionalRepository.findByProfessionalRegistration(professional.getProfessionalRegistration());
            if (prof != null) {
                throw new AppException("El profesional ya se encuentra registrado", HttpStatus.INTERNAL_SERVER_ERROR);
            }
          
            Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(specialitiesIds));
            if (specialities.size() != specialitiesIds.size()) {
                throw new AppException("Una o más especialidades no encontradas", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            professional.setSpecialities(specialities);

            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(institutionsIds));
            if (institutions.size() != institutionsIds.size()) {
                throw new AppException("Una o más instituciones no encontradas", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            professional.setInstitutions(institutions);

           return this.professionalRepository.save(professional);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Professional getProfessional(int id) {
        try{
            return this.professionalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
        } catch (Exception e)    {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Professional> getAllProfessionals() {
        try {
            List<Professional> professionals = this.professionalRepository.findAll();
            if (professionals.isEmpty()) {
                throw new AppException("No existen profesionales", HttpStatus.NOT_FOUND);
            }
            return professionals;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteProfessional(int id) {
        try{
            Professional professional  = this.professionalRepository.findById(id)
                    .orElseThrow(()-> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
            this.professionalRepository.delete(professional);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Professional updateProfessional(Professional professional){
        try{
            Professional prof = this.professionalRepository.findById(professional.getIdProfessional())
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
            prof.setProfessionalRegistration(professional.getProfessionalRegistration());
            prof.setProfessionalName(professional.getProfessionalName());
            return this.professionalRepository.save(prof);
        }catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
