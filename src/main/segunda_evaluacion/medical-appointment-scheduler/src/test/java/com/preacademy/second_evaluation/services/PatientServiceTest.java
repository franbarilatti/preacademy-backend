package com.preacademy.second_evaluation.services;

import com.preacademy.second_evaluation.exceptions.ResourceNotFoundException;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Juan");
        patient.setLastName("Perez");
        patient.setDni("12345678");
        patient.setEmail("juan.perez@example.com");
    }

    @Test
    void createPatient_shouldSaveAndReturnPatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.createPatient(patient);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getName());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void getPatientById_shouldReturnPatient_whenFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("juan.perez@example.com", result.getEmail());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void getPatientById_shouldThrowResourceNotFoundException_whenNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById(1L));
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void getAllPatients_shouldReturnAllPatients() {
        Map<Long, Patient> patients = new HashMap<>();
        patients.put(1L, patient);
        when(patientRepository.findAll()).thenReturn(patients);

        Map<Long, Patient> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals(patient, result.get(1L));
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void deletePatient_shouldDelete_whenPatientExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).delete(1L);
    }

    @Test
    void deletePatient_shouldThrowResourceNotFoundException_whenNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(1L));
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, never()).delete(anyLong());
    }
}
