package com.backend.easyturn.services;

import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.repositories.SpecialityRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialityService {

    @Autowired
    private final SpecialityRepository specialityRepository;

    public void createSpeciality(Speciality speciality) {
        this.specialityRepository.save(speciality);
    }

    public List<Speciality> findAllSpecialities() {
        return this.specialityRepository.findAll(Sort.by("specialityName"));
    }

    public Optional<Speciality> findSpecialityById(Long idSpeciality) {
        return this.specialityRepository.findById(idSpeciality);
    }

    public void deleteSpecialityById(Long idSpeciality) {
        this.specialityRepository.deleteById(idSpeciality);
    }

}
