package com.preacademy.second_evaluation.services;


import com.preacademy.second_evaluation.exceptions.InvalidDataException;
import com.preacademy.second_evaluation.exceptions.ResourceNotFoundException;
import com.preacademy.second_evaluation.models.Appointment;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.repositories.AppointmentRepository;
import com.preacademy.second_evaluation.repositories.PatientRepository;
import com.preacademy.second_evaluation.repositories.ProfessionalRepository;
import com.preacademy.second_evaluation.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Patient patient;
    private Professional professional;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Juan");
        patient.setLastName("Perez");
        patient.setDni("12345678");
        patient.setEmail("juan.perez@example.com");

        professional = new Professional();
        professional.setId(1L);
        professional.setFullName("Dra. Ana Tomia");
        professional.setSpecialty("Clínica");

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        appointment.setDate(LocalDate.of(2025, 8, 10));
    }

    @Test
    void createAppointment_shouldSaveAndReturnAppointment_whenValid() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(professionalRepository.findAll()).thenReturn(Map.of(1L, professional));
        when(appointmentRepository.findAll()).thenReturn(new HashMap<>());
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.createAppointment(appointment);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPatient().getId());
        assertEquals(1L, result.getProfessional().getId());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void createAppointment_shouldThrowResourceNotFoundException_whenPatientNotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.createAppointment(appointment));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void createAppointment_shouldThrowResourceNotFoundException_whenProfessionalNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(professionalRepository.findAll()).thenReturn(new HashMap<>());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.createAppointment(appointment));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void createAppointment_shouldThrowInvalidDataException_whenDuplicate() {
        Map<Long, Appointment> existingAppointments = new HashMap<>();
        existingAppointments.put(1L, appointment);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(professionalRepository.findAll()).thenReturn(Map.of(1L, professional));
        when(appointmentRepository.findAll()).thenReturn(existingAppointments);

        assertThrows(InvalidDataException.class, () -> appointmentService.createAppointment(appointment));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void getAllAppointments_shouldReturnAllAppointments() {
        Map<Long, Appointment> appointments = new HashMap<>();
        appointments.put(1L, appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        Map<Long, Appointment> result = appointmentService.getAllAppointments();

        assertEquals(1, result.size());
        assertEquals(appointment, result.get(1L));
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void getAppointmentsByDate_shouldReturnMatchingAppointments() {
        List<Appointment> appointments = List.of(appointment);
        when(appointmentRepository.findByDate(LocalDate.of(2025, 8, 10))).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByDate(LocalDate.of(2025, 8, 10));

        assertEquals(1, result.size());
        assertEquals(appointment, result.get(0));
        verify(appointmentRepository, times(1)).findByDate(LocalDate.of(2025, 8, 10));
    }

    @Test
    void getAppointmentsByDateRange_shouldReturnMatchingAppointments() {
        List<Appointment> appointments = List.of(appointment);
        when(appointmentRepository.findByDateRange(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 12)))
                .thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByDateRange(
                LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 12));

        assertEquals(1, result.size());
        assertEquals(appointment, result.get(0));
        verify(appointmentRepository, times(1)).findByDateRange(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 12));
    }

    @Test
    void getAppointmentsByDateRange_shouldThrowInvalidDataException_whenInvalidRange() {
        assertThrows(InvalidDataException.class, () -> appointmentService.getAppointmentsByDateRange(
                LocalDate.of(2025, 8, 12), LocalDate.of(2025, 8, 10)));
        verify(appointmentRepository, never()).findByDateRange(any(), any());
    }

    @Test
    void deleteAppointment_shouldDelete_whenAppointmentExists() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).delete(1L);
    }

    @Test
    void deleteAppointment_shouldThrowResourceNotFoundException_whenNotFound() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.deleteAppointment(1L));
        verify(appointmentRepository, times(1)).findById(1L);
        verify(appointmentRepository, never()).delete(anyLong());
    }
}
