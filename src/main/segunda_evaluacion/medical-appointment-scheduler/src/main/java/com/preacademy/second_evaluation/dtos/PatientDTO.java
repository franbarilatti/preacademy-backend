package com.preacademy.second_evaluation.dtos;

import lombok.Data;

@Data
public class PatientDTO {

    private Long id;
    private String name;
    private String lastName;
    private String dni;
    private String email;

}
