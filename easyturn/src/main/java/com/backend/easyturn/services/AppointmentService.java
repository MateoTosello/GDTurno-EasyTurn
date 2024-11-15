package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.entities.Speciality;
import com.backend.easyturn.exceptions.AppException;
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
                    .orElseThrow(() -> new AppException("El paciente no existe", HttpStatus.NOT_FOUND));

            // Verificar que el profesional existe
            Professional professional = this.professionalRepository.findById(idProfessional)
                    .orElseThrow(() -> new AppException("El profesional no existe", HttpStatus.NOT_FOUND));

            // Verificar que la especialidad existe
            Speciality speciality = this.specialityRepository.findById(idSpeciality)
                    .orElseThrow(() -> new AppException("La especialidad no existe", HttpStatus.NOT_FOUND));
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
                throw new AppException(
                        "Ya existe un turno para ese profesional en ese horario o muy cercano",
                        HttpStatus.CONFLICT
                );
            }

            appointment.setPatient(patient);
            appointment.setProfessional(professional);
            appointment.setSpeciality(speciality);

            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Appointment> getAllAppointments() {
    try {
        List<Appointment> appointments = this.appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new AppException("No hay turnos cargados", HttpStatus.NOT_FOUND);
        }
        return appointments;
    } catch (Exception e) {
        throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    public Appointment getAppointmentById(int idAppointment) {
    try {
        return this.appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new AppException("El turno no existe", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
        throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    public void deleteAppointment(int idAppointment) {
        try {
            Appointment a = this.appointmentRepository.findById(idAppointment)
                    .orElseThrow(() -> new AppException("El turno no existe", HttpStatus.NOT_FOUND));
            this.appointmentRepository.delete(a);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Appointment updateAppointment(Appointment appointment) {
        try {
            Appointment existingAppointment = this.appointmentRepository.findById(appointment.getIdAppointment())
                    .orElseThrow(() -> new AppException("Turno no encontrado", HttpStatus.NOT_FOUND));

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
                        throw new AppException(
                                "Ya existe un turno para ese profesional en ese horario o muy cercano",
                                HttpStatus.CONFLICT
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
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

