package com.preacademy.second_evaluation.services;

import com.preacademy.second_evaluation.exceptions.ResourceNotFoundException;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.repositories.PatientRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient) {
        logger.info("Creando un patient con id " + patient.getId());
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) {
        logger.info("Consultando un paciente con id " + id);
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Paciente no encontrado con la id: {}", id);
                    return new ResourceNotFoundException("Paciente no encontrado con la id: " + id);
                });

    }

    public Map<Long, Patient> getAllPatients(){
        logger.info("Consultando todos los pacientes");
        return patientRepository.findAll();
    }

    public void deletePatient(Long id) {
        logger.info("Eliminando un paciente con id " + id);
        if (!patientRepository.findById(id).isPresent()) {
            logger.error("Paciente no encontrado con la id: {}", id);
            throw new ResourceNotFoundException("Paciente no encontrado con la id: "+ id);
        }
        patientRepository.delete(id);
    }


}
