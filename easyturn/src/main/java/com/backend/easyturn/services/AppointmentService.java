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
import java.time.LocalTime;
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

    //horario de corrido de 08 a 16hs. turnos cada media hora.
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(16, 0);

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

            // Verificar que la especialidad es una del profesional
            if (!professional.getSpecialities().contains(speciality)) {
                throw new AppException("El profesional no atiende la especialidad solicitada", HttpStatus.CONFLICT);
            }

            LocalDateTime appointmentDateTime = appointment.getAppointmentDateTime();

            // Validar que fecha y hora sea posterior al momento de crear el turno
            LocalDateTime now = LocalDateTime.now();
            if (appointmentDateTime.isBefore(now)) {
                throw new AppException("El turno debe ser posterior a la fecha y hora actual", HttpStatus.BAD_REQUEST);
            }

            // Validar horario de atención
            LocalTime time = appointmentDateTime.toLocalTime();
            if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
                throw new AppException("El horario del turno debe estar entre las 8:00 y 16:00 hs", HttpStatus.BAD_REQUEST);
            }

            // Validar turnos en punto y media hora
            int minutes = time.getMinute();
            if (minutes != 0 && minutes != 30) {
                throw new AppException("Los turnos solo pueden ser en punto o y media (HH:00 o HH:30)", HttpStatus.BAD_REQUEST);
            }

            // Verificar disponibilidad del turno
            if (appointmentRepository.existsByProfessionalAndAppointmentDateTime(professional, appointmentDateTime)) {
                throw new AppException("Ya existe un turno para ese profesional en esa fecha y horario", HttpStatus.CONFLICT);
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

                // Validar que fecha y hora sea posterior al momento de crear el turno
                LocalDateTime now = LocalDateTime.now();
                if (appointment.getAppointmentDateTime().isBefore(now)) {
                    throw new AppException("El turno debe ser posterior a la fecha y hora actual", HttpStatus.BAD_REQUEST);
                }

                // Validar horario de atención
                LocalTime time = appointment.getAppointmentDateTime().toLocalTime();
                if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(16, 0))) {
                    throw new AppException("El horario del turno debe estar entre las 8:00 y 16:00 hs", HttpStatus.BAD_REQUEST);
                }

                // Validar turnos en punto y media hora
                int minutes = time.getMinute();
                if (minutes != 0 && minutes != 30) {
                    throw new AppException("Los turnos solo pueden ser en punto o y media (HH:00 o HH:30)", HttpStatus.BAD_REQUEST);
                }

                // Verificar que si se modificó la fecha, no tenga conflictos de superposición
                if (!existingAppointment.getAppointmentDateTime().equals(appointment.getAppointmentDateTime())) {
                    // verificar si hay algun turno en dicho horario para el profesional del turno
                    if (appointmentRepository.existsByProfessionalAndAppointmentDateTime(
                            existingAppointment.getProfessional(), appointment.getAppointmentDateTime())) {
                        throw new AppException(
                                "Ya existe un turno para ese profesional en ese horario", HttpStatus.CONFLICT
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

