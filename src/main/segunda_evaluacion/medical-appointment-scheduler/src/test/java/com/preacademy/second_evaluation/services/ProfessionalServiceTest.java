package com.preacademy.second_evaluation.services;

import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.repositories.ProfessionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessionalServiceTest {
    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private ProfessionalService professionalService;

    private Professional professional;

    @BeforeEach
    void setUp() {
        professional = new Professional();
        professional.setId(1L);
        professional.setFullName("Dra. Ana Tomia");
        professional.setSpecialty("Clínica");
    }

    @Test
    void createProfessional_shouldSaveAndReturnProfessional() {
        when(professionalRepository.save(any(Professional.class))).thenReturn(professional);

        Professional result = professionalService.create(professional);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Clínica", result.getSpecialty());
        verify(professionalRepository, times(1)).save(professional);
    }


    @Test
    void getProfessionalsBySpecialty_shouldReturnMatchingProfessionals() {
        List<Professional> professionals = List.of(professional);
        Mockito.when(professionalRepository.findBySpecialty("Clinica")).thenReturn(professionals);

        List<Professional> result = professionalService.getProfessionalBySpecialty("Clínica");

        assertEquals(1, result.size());
        assertEquals("Dra. Ana Tomia", result.get(0).getFullName());
        verify(professionalRepository, times(1)).findBySpecialty("Clínica");
    }

    @Test
    void getAllProfessionals_shouldReturnAllProfessionals() {
        Map<Long, Professional> professionals = new HashMap<>();
        professionals.put(1L, professional);
        Mockito.when(professionalRepository.findAll()).thenReturn(professionals);

        Map<Long, Professional> result = professionalService.getAllProfessionals();

        assertEquals(1, result.size());
        assertEquals(professional, result.get(1L));
        verify(professionalRepository, times(1)).findAll();
    }
}
