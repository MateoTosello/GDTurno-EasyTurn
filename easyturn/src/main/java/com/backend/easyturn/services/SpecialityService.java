package com.backend.easyturn.services;

import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialityService {
    @Autowired
    private final SpecialityRepository specialityRepository;

    public Speciality createSpeciality(Speciality speciality) {
        try {
            Speciality spec = this.specialityRepository.findBySpecialityName(speciality.getSpecialityName());
            if (spec != null){
                throw  new AppException("La especialidad ya se encuentra registrada",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return specialityRepository.save(speciality);
        } catch (Exception e) {
            throw new AppException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Speciality getSpecialityById(Long idSpeciality) {
        try{
            return this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(()-> new AppException("Especialidad no encontrada",HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Speciality> getAllSpecialities() {
        try{
            List<Speciality> specialities = this.specialityRepository.findAll(Sort.by("specialityName"));
            if (specialities.isEmpty()){
                throw new AppException("No existen especialidades",HttpStatus.NOT_FOUND);
            }
            return specialities;
        } catch (Exception e) {
            throw new AppException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteSpecialityById(Long idSpeciality) {
        try{
            Speciality speciality  = this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(()-> new AppException("Especialidad no encontrada", HttpStatus.NOT_FOUND));
            this.specialityRepository.delete(speciality);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Speciality updateSpeciality(Speciality speciality) {
        try {
            Speciality sp = this.specialityRepository.findById(speciality.getIdSpeciality())
                    .orElseThrow(() -> new AppException("Especialidad no encontrada", HttpStatus.NOT_FOUND));
            sp.setSpecialityName(speciality.getSpecialityName());
            sp.setSpecialityDescription(speciality.getSpecialityDescription());
            return this.specialityRepository.save(sp);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

