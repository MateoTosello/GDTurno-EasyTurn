package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.DiagnosisDTO;
import com.backend.easyturn.entities.Diagnosis;
import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.repositories.AppointmentRepository;
import com.backend.easyturn.repositories.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Diagnosis createDiagnosis(int appointmentId, Diagnosis diagnosis) {
        try{
            //Verificar que el turno ingresado existe
            Appointment appointment =  this.appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new AppException("No se encontró el turno", HttpStatus.NOT_FOUND));

            //Verificar que el turno ya no tiene ingresado un diagnostico
            if (diagnosisRepository.existsByAppointmentIdAppointment(appointmentId)){
                throw new AppException("El turno ingresado ya posee un registro de atención.",HttpStatus.CONFLICT);
            }

            //Verificar que la descripcion no sea nula o vacía
            if (diagnosis.getDiagnosisDescription() == null || diagnosis.getDiagnosisDescription().trim().isEmpty()){
                throw new AppException("La descripción de la atención médica no puede estar vacía",HttpStatus.INTERNAL_SERVER_ERROR);
            }

            diagnosis.setAppointment(appointment);
            appointment.setDiagnosis(diagnosis);

            return this.diagnosisRepository.save(diagnosis);
        }
        catch (Exception e){
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Diagnosis getDiagnosticById(int diagnosisId) {
        try {
            return diagnosisRepository.findById(diagnosisId)
                    .orElseThrow(() -> new AppException("No se encontró el diagnóstico", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<Diagnosis> getAllDiagnosis() {
        try {
            List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
            if (diagnosisList.isEmpty()) {
                throw new AppException("No hay diagnósticos registrados", HttpStatus.NOT_FOUND);
            }
            return diagnosisList;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteDiagnosis(int id) {
        try {
            Diagnosis diagnosis = this.diagnosisRepository.findById(id)
                    .orElseThrow(() -> new AppException("Diagnóstico no encontrado", HttpStatus.NOT_FOUND));

            // Obtener el turno asociado
            Appointment appointment = diagnosis.getAppointment();
            if (appointment != null) {
                // Desasociar el diagnóstico del turno
                appointment.setDiagnosis(null);
                // Guardar el turno actualizado
                appointmentRepository.save(appointment);
            }

            // Desasociar el turno del diagnóstico
            diagnosis.setAppointment(null);

            // Eliminar el diagnóstico
            this.diagnosisRepository.delete(diagnosis);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Diagnosis updateDiagnosis(Diagnosis diagnosisUpdate) {
        try {
            Diagnosis diagnosis = this.diagnosisRepository.findById(diagnosisUpdate.getIdDiagnosis())
                    .orElseThrow(() -> new AppException("Diagnóstico no encontrado", HttpStatus.NOT_FOUND));

            diagnosis.setDiagnosisDescription(diagnosisUpdate.getDiagnosisDescription());
            return this.diagnosisRepository.save(diagnosis);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
