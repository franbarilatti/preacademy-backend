package com.preacademy.second_evaluation.controllers;


import com.preacademy.second_evaluation.dtos.PatientDTO;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.services.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO){
        logger.info("Creando un paciente con id {}", patientDTO.getId());
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setLastName(patientDTO.getLastName());
        patient.setDni(patientDTO.getDni());
        patient.setEmail(patientDTO.getEmail());

        Patient savedPatient = patientService.createPatient(patient);
        patientDTO.setId(savedPatient.getId());
        return ResponseEntity.ok(patientDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id){
        logger.info("Consultando un paciente con id {}", id);
        Patient patient = patientService.getPatientById(id);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setDni(patient.getDni());
        patientDTO.setEmail(patient.getEmail());
        return ResponseEntity.ok(patientDTO);
    }

    @GetMapping
    public ResponseEntity<Map<Long, PatientDTO>> getAllPatients(){
        logger.info("Consultando todos los patientes");
        Map<Long, PatientDTO> patients = patientService.getAllPatients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            PatientDTO patientDTO = new PatientDTO();
                            patientDTO.setId(entry.getValue().getId());
                            patientDTO.setName(entry.getValue().getName());
                            patientDTO.setLastName(entry.getValue().getLastName());
                            patientDTO.setDni(entry.getValue().getDni());
                            patientDTO.setEmail(entry.getValue().getEmail());
                            return patientDTO;
                        }
                ));
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        logger.info("Eliminando un paciente con id {}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }



}
