package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                throw new AppException("El profesional no puede ser nulo", HttpStatus.BAD_REQUEST);
            }

            Professional prof = this.professionalRepository.findByProfessionalRegistration(professional.getProfessionalRegistration());
            if (prof != null) {
                throw new AppException("El profesional ya se encuentra registrado", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Validar que specialitiesIds no esté vacío
            if (specialitiesIds == null || specialitiesIds.isEmpty()) {
                throw new AppException("Debe proporcionar al menos una especialidad", HttpStatus.BAD_REQUEST);
            }

            // Validar que institutionsIds no esté vacío
            if (institutionsIds == null || institutionsIds.isEmpty()) {
                throw new AppException("Debe proporcionar al menos una institución", HttpStatus.BAD_REQUEST);
            }

            Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(specialitiesIds));
            if (specialities.size() != specialitiesIds.size()) {
                throw new AppException("Una o más especialidades no encontradas", HttpStatus.NOT_FOUND);
            }
            professional.setSpecialities(specialities);

            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(institutionsIds));
            if (institutions.size() != institutionsIds.size()) {
                throw new AppException("Una o más instituciones no encontradas", HttpStatus.NOT_FOUND);
            }
            professional.setInstitutions(institutions);

            return this.professionalRepository.save(professional);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Professional getProfessional(int id) {
        try {
            return this.professionalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
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
        try {
            Professional professional = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
            this.professionalRepository.delete(professional);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Professional updateProfessional(Professional professional) {
        try {
            Professional prof = this.professionalRepository.findById(professional.getIdProfessional())
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));
            prof.setProfessionalRegistration(professional.getProfessionalRegistration());
            prof.setProfessionalName(professional.getProfessionalName());
            return this.professionalRepository.save(prof);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Professional addSpecialities(int id, List<Long> specialitiesIds) {
        try {
            if (specialitiesIds.isEmpty()) {
                throw new AppException("Lista de especialidades vacía", HttpStatus.BAD_REQUEST);
            }

            Professional prof = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));

            Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(specialitiesIds));
            if (specialities.size() != specialitiesIds.size()) {
                throw new AppException("Una o mas especialidades no encontradas", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            prof.getSpecialities().addAll(specialities);

            return this.professionalRepository.save(prof);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Professional addInstitutions(int id, List<Integer> institutionsIds) {
        try {
            if (institutionsIds.isEmpty()) {
                throw new AppException("Lista de instituciones vacía", HttpStatus.BAD_REQUEST);
            }

            Professional prof = this.professionalRepository.findById(id)
                    .orElseThrow(() -> new AppException("Profesional no encontrado", HttpStatus.NOT_FOUND));

            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(institutionsIds));
            if (institutions.size() != institutionsIds.size()) {
                throw new AppException("Una o mas instituciones no encontradas", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            prof.getInstitutions().addAll(institutions);

            return this.professionalRepository.save(prof);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Set<Appointment> getAppointmentsByProfessional(int idProfessional) {
        try {
            Professional professional = this.professionalRepository.findById(idProfessional)
                    .orElseThrow(() -> new AppException("El profesional no existe", HttpStatus.NOT_FOUND));
            return professional.getAppointments();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Set<Appointment> listAppointmentsByDate(Integer idProfessional, LocalDate date) {
        // Validar que el ID del profesional no sea nulo
        if (idProfessional == null) {
            throw new AppException("El ID del profesional no puede ser nulo", HttpStatus.BAD_REQUEST);
        }

        // Validar que la fecha no sea nula
        if (date == null) {
            throw new AppException("La fecha no puede ser nula", HttpStatus.BAD_REQUEST);
        }

        // Verificar que el profesional existe
        if (!professionalRepository.existsById(idProfessional)) {
            throw new AppException("El profesional no existe", HttpStatus.NOT_FOUND);
        }

        try {
            // Obtener los turnos del profesional en la fecha especificada
            return professionalRepository.findAppointmentsByProfessionalAndDate(idProfessional, date);
        } catch (Exception e) {
            throw new AppException("Error al obtener los turnos: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}