package com.preacademy.second_evaluation.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Appointment {

    @NotNull(message = "EL ID no puede ser nulo")
    private Long id;

    @NotNull(message = "El paciente no puede ser nulo")
    private Patient patient;

    @NotNull(message = "El profecional no puede ser nulo")
    private Professional professional;

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate date;


}
