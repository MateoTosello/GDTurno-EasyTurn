package com.backend.easyturn.services;

import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public Institution createInstitution(Institution institution) {
        try{
            Institution ins =  this.institutionRepository.findByInstitutionName(institution.getInstitutionName());
            if (ins != null) {
                throw new AppException("Institución existente", HttpStatus.CONFLICT);
            }
            return this.institutionRepository.save(institution);
        }
        catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }

    }

    public Institution getInstitution(int id) {
        try{
            return this.institutionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Institution no encontrada", HttpStatus.NOT_FOUND));
        }
        catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public List<Institution> getAllInstitutions(){
        try{
           List<Institution> institutions = this.institutionRepository.findAll();
           if(institutions.isEmpty()){
                throw new AppException("No hay instituciones", HttpStatus.NOT_FOUND);
           }
           return institutions;
        }
        catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public void deleteInstitution(int id) {
        try{
            Institution institution = this.institutionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Institution no encontrada", HttpStatus.NOT_FOUND));
            this.institutionRepository.delete(institution);
        }
        catch (AppException e){
            throw new AppException(e.getMessage(),e.getStatus());
        }
    }

    public Institution updateInstitution(Institution institution) {
        try{
            Institution ins = this.institutionRepository.findById(institution.getIdInstitution())
                    .orElseThrow(() -> new AppException("Institution no encontrada", HttpStatus.NOT_FOUND));
            ins.setInstitutionName(institution.getInstitutionName());
            ins.setInstitutionAddress(institution.getInstitutionAddress());
            ins.setInstitutionAddressNumber(institution.getInstitutionAddressNumber());
            return this.institutionRepository.save(ins);
        }
        catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }




}
