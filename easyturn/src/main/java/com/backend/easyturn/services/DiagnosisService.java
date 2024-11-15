package com.backend.easyturn.services;

import com.backend.easyturn.entities.Appointment;
import com.backend.easyturn.entities.DTOs.DiagnosisDTO;
import com.backend.easyturn.entities.Diagnosis;
import com.backend.easyturn.entities.Institution;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.IfClassExistsException;
import com.backend.easyturn.exceptions.NotFoundException;
import com.backend.easyturn.exceptions.RequestIncompleteException;
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
                    .orElseThrow(() -> new NotFoundException("No se encontró el turno"));

            //Verificar que el turno ya no tiene ingresado un diagnostico
            if (diagnosisRepository.existsByAppointmentIdAppointment(appointmentId)){
                throw new IfClassExistsException("El turno ingresado ya posee un registro de atención.");
            }

            //Verificar que la descripcion no sea nula o vacía
            if (diagnosis.getDiagnosisDescription() == null || diagnosis.getDiagnosisDescription().trim().isEmpty()){
                throw new RequestIncompleteException("La descripción de la atención médica no puede estar vacía");
            }

            diagnosis.setAppointment(appointment);
            appointment.setDiagnosis(diagnosis);

            return this.diagnosisRepository.save(diagnosis);
        }
        catch (AppException e){
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }


    public Diagnosis getDiagnosticById(int diagnosisId) {
        try {
            return diagnosisRepository.findById(diagnosisId)
                    .orElseThrow(() -> new NotFoundException("No se encontró el diagnóstico"));
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }


    public List<Diagnosis> getAllDiagnosis() {
        try {
            List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
            if (diagnosisList.isEmpty()) {
                throw new NotFoundException("No hay diagnósticos registrados");
            }
            return diagnosisList;
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public void deleteDiagnosis(int id) {
        try {
            Diagnosis diagnosis = this.diagnosisRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Diagnóstico no encontrado"));

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
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }

    public Diagnosis updateDiagnosis(Diagnosis diagnosisUpdate) {
        try {
            Diagnosis diagnosis = this.diagnosisRepository.findById(diagnosisUpdate.getIdDiagnosis())
                    .orElseThrow(() -> new NotFoundException("Diagnóstico no encontrado"));

            diagnosis.setDiagnosisDescription(diagnosisUpdate.getDiagnosisDescription());
            return this.diagnosisRepository.save(diagnosis);
        } catch (AppException e) {
            throw new AppException(e.getMessage(), e.getStatus());
        }
    }
}
