package com.preacademy.second_evaluation.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDTO {

    private Long id;
    private Long patientId;
    private Long professionalId;
    private LocalDate date;

}
