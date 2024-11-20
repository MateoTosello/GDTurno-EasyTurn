package com.backend.easyturn.services;

import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
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
                throw  new IfClassExistsException("La especialidad ya se encuentra registrada");
            }
            return specialityRepository.save(speciality);
        } catch (AppException e) {
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public Speciality getSpecialityById(Long idSpeciality) {
        try{
            return this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(()-> new NotFoundException("Especialidad no encontrada"));
        } catch (AppException e) {
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public List<Speciality> getAllSpecialities() {
        try{
            List<Speciality> specialities = this.specialityRepository.findAll();
            if (specialities.isEmpty()){
                throw new NotFoundException("No existen especialidades");
            }
            return specialities;
        } catch (AppException e) {
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public void deleteSpecialityById(Long idSpeciality) {
        try{
            Speciality speciality  = this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(()-> new NotFoundException("Especialidad no encontrada"));
            this.specialityRepository.delete(speciality);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Speciality updateSpeciality(Speciality speciality) {
        try {
            Speciality sp = this.specialityRepository.findById(speciality.getIdSpeciality())
                    .orElseThrow(() -> new AppException("Especialidad no encontrada", HttpStatus.NOT_FOUND));

            if (speciality.getSpecialityName() != null) {
                sp.setSpecialityName(speciality.getSpecialityName());
            }
            if (speciality.getSpecialityDescription() != null) {
                sp.setSpecialityDescription(speciality.getSpecialityDescription());
            }
            return this.specialityRepository.save(sp);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<String> getAllSpecialityNames() {
        try {
            List<Speciality> specialities = this.specialityRepository.findAll(Sort.by("specialityName"));
            if (specialities.isEmpty()) {
                throw new AppException("No existen especialidades", HttpStatus.NOT_FOUND);
            }
            return specialities.stream()
                    .map(Speciality::getSpecialityName)
                    .toList();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

