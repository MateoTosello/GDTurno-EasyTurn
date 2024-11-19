package com.backend.easyturn.controllers;


import com.backend.easyturn.entities.DTOs.DiagnosisDTO;
import com.backend.easyturn.entities.DTOs.ProfessionalDTO;
import com.backend.easyturn.entities.Diagnosis;
import com.backend.easyturn.entities.Professional;
import com.backend.easyturn.requests.DiagnosisRequest;
import com.backend.easyturn.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @CrossOrigin(origins = "*")
    @PostMapping("/post")
    public ResponseEntity<DiagnosisDTO> createDiagnosis(@RequestBody DiagnosisRequest request) {
        Diagnosis diagnosisCreated = this.diagnosisService.createDiagnosis(request.getAppointmentId(), request.getDiagnosis());
        return new ResponseEntity<>(diagnosisCreated.toDTO(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-diagnosis/{id}")
    @ResponseBody
    public ResponseEntity<DiagnosisDTO> getDiagnosticById(@PathVariable int id) {
        Diagnosis diagnosis = this.diagnosisService.getDiagnosticById(id);
        return new ResponseEntity<>(diagnosis.toDTO(),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-diagnosis")
    @ResponseBody
    public ResponseEntity<List<DiagnosisDTO>> getAll() {
        List<Diagnosis> diagnosisList = this.diagnosisService.getAllDiagnosis();
        List<DiagnosisDTO> diagnosisDTOS = diagnosisList.stream()
                .map(Diagnosis::toDTO)
                .toList();
        return new ResponseEntity<>(diagnosisDTOS, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete-diagnosis/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable int id) {
        this.diagnosisService.deleteDiagnosis(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update-diagnosis")
    public ResponseEntity<DiagnosisDTO> updateDiagnostic(@RequestBody Diagnosis diagnosis) {
        Diagnosis diagnosisUpdated = this.diagnosisService.updateDiagnosis(diagnosis);
        return new ResponseEntity<>(diagnosisUpdated.toDTO(),HttpStatus.OK);
    }

}
