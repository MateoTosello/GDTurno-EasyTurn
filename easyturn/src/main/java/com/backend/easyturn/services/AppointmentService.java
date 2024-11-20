package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.repositories.AppointmentRepository;
import com.backend.easyturn.repositories.PatientRepository;
import com.backend.easyturn.repositories.ProfessionalRepository;
import com.backend.easyturn.repositories.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    public Appointment createAppointment(Appointment appointment, int idPatient, int idProfessional, Long idSpeciality) {
        try {
            // Verificar que el paciente existe
            Patient patient = this.patientRepository.findById(idPatient)
                    .orElseThrow(() -> new NotFoundException("El paciente no existe"));

            // Verificar que el profesional existe
            Professional professional = this.professionalRepository.findById(idProfessional)
                    .orElseThrow(() -> new NotFoundException("El profesional no existe"));

            // Verificar que la especialidad existe
            Speciality speciality = this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(() -> new NotFoundException("La especialidad no existe"));
            // Verificar que la especialidad es una de

            if (!professional.getSpecialities().contains(speciality)) {
                throw new AppException("El profesional no atiende la especialidad solicitada", HttpStatus.CONFLICT);
            }

            // Definir el rango de tiempo (por ejemplo, 30 minutos antes y después) (Turnos cada 30 min)
            LocalDateTime startTime = appointment.getAppointmentDateTime().minusMinutes(29);
            LocalDateTime endTime = appointment.getAppointmentDateTime().plusMinutes(29);
            // Verificar si existe algún turno en ese rango de tiempo
            if(appointmentRepository.existsByProfessionalAndAppointmentDateTimeBetween(
                    professional, startTime, endTime)) {
                throw new IfClassExistsException(
                        "Ya existe un turno para ese profesional en ese horario o muy cercano"
                );
            }

            appointment.setPatient(patient);
            appointment.setProfessional(professional);
            appointment.setSpeciality(speciality);

            return appointmentRepository.save(appointment);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }

    }

    public List<Appointment> getAllAppointments() {
    try {
        List<Appointment> appointments = this.appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new NotFoundException("No hay turnos cargados");
        }
        return appointments;
    } catch (AppException e) {
        throw new AppException(e.getMessage(), e.getStatus());
    }
}


    public Appointment getAppointmentById(int idAppointment) {
    try {
        return this.appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new NotFoundException("El turno no existe"));
    } catch (AppException e) {
        throw new AppException(e.getMessage(), e.getStatus());
    }
}

    public void deleteAppointment(int idAppointment) {
        try {
            Appointment a = this.appointmentRepository.findById(idAppointment)
                    .orElseThrow(() -> new NotFoundException("El turno no existe"));
            this.appointmentRepository.delete(a);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }


    public Appointment updateAppointment(Appointment appointment) {
        try {
            Appointment existingAppointment = this.appointmentRepository.findById(appointment.getIdAppointment())
                    .orElseThrow(() -> new NotFoundException("Turno no encontrado"));

            // Actualizar solo los campos que están presentes en el appointment recibido

            if (appointment.getAppointmentDateTime() != null) {
                // Verificar que si se modificó la fecha, no tenga conflictos de superposición
                if (!existingAppointment.getAppointmentDateTime().equals(appointment.getAppointmentDateTime())) {
                    // Definir el rango de tiempo
                    LocalDateTime startTime = appointment.getAppointmentDateTime().minusMinutes(29);
                    LocalDateTime endTime = appointment.getAppointmentDateTime().plusMinutes(29);
                    // verificar si hay algun turno en dicho rango para el profesional del turno
                    if (appointmentRepository.existsByProfessionalAndAppointmentDateTimeBetween(
                            existingAppointment.getProfessional(), startTime, endTime)) {
                        throw new IfClassExistsException(
                                "Ya existe un turno para ese profesional en ese horario o muy cercano"
                        );
                    }
                    existingAppointment.setAppointmentDateTime(appointment.getAppointmentDateTime());
                }
            }

            if (appointment.getAppointmentStatus() != null) {
                existingAppointment.setAppointmentStatus(appointment.getAppointmentStatus());
            }

            if (appointment.getPatientValoration() != null) {
                existingAppointment.setPatientValoration(appointment.getPatientValoration());
            }
            return appointmentRepository.save(existingAppointment);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }
}

