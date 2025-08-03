package com.preacademy.second_evaluation.repositories;

import com.preacademy.second_evaluation.models.Patient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PatientRepository {

    private final Map<Long, Patient> patients = new HashMap<>();

    public Patient save(Patient patient) {
        patients.put(patient.getId(), patient);
        return patient;
    }

    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(patients.get(id));
    }

    public Map<Long, Patient> findAll() {
        return new HashMap<>(patients);
    }

    public void delete(Long id) {
        patients.remove(id);
    }

}
