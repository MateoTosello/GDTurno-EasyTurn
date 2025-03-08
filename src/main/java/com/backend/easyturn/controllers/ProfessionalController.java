package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.AppointmentDTO;
import com.backend.easyturn.entities.DTOs.AppointmentSearchDateDTO;
import com.backend.easyturn.entities.DTOs.AppointmentShortDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.requests.ProfessionalRequest;
import com.backend.easyturn.services.ProfessionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @CrossOrigin(origins = "*")
    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> createProfessional(@RequestBody ProfessionalRequest request) {
        Professional professionalCreated = this.professionalService.createProfessional(request.getProfessional(), request.getSpecialitiesIds(), request.getInstitutionsIds());
        return new ResponseEntity<>(professionalCreated.toDTO(), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-professional/{id}")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> getProfessional(@PathVariable int id) {
        Professional professional = this.professionalService.getProfessional(id);
        return new ResponseEntity<>(professional.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-professionals")
    @ResponseBody
    public ResponseEntity<List<ProfessionalDTO>> getAll() {
        List<Professional> professionals = this.professionalService.getAllProfessionals();
        List<ProfessionalDTO> professionalsDTO = professionals.stream()
                .map(Professional::toDTO)
                .toList();
        return new ResponseEntity<>(professionalsDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete-professional/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteProfessional(@PathVariable int id) {
        this.professionalService.deleteProfessional(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update-professional")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> updateProfessional(@RequestBody Professional professional) {
        Professional professionalUpdated = this.professionalService.updateProfessional(professional);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("add-specialities/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> addSpeciality(@PathVariable int id, @RequestBody List<Long> specialitiesIds){
        Professional professionalUpdated = this.professionalService.addSpecialities(id,specialitiesIds);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("add-institutions/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @ResponseBody
    public ResponseEntity<ProfessionalDTO> addInstitution(@PathVariable int id, @RequestBody List<Integer> institutionsIds){
        Professional professionalUpdated = this.professionalService.addInstitutions(id,institutionsIds);
        return new ResponseEntity<>(professionalUpdated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/appointments/{id}")
    @ResponseBody
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByProfessional(@PathVariable int id) {
        Set<Appointment> appointments = this.professionalService.getAppointmentsByProfessional(id);
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(Appointment::toDTO)
                .toList();
        return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
    }

    @GetMapping("/appointments/date")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public ResponseEntity<List<AppointmentShortDTO>> getAppointmentsByDate(@RequestBody AppointmentSearchDateDTO filterDateDTO) {

        Set<Appointment> appointments = this.professionalService.listAppointmentsByDate(filterDateDTO.getIdProfessional(),filterDateDTO.getDate());

        List<AppointmentShortDTO> appointmentDTOs = appointments.stream()
                .map(Appointment::toShortDTO)
                .toList();

        return new ResponseEntity<>(appointmentDTOs, HttpStatus.OK);
    }
} 
