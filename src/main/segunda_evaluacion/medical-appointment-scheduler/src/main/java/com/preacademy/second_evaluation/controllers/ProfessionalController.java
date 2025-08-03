package com.preacademy.second_evaluation.controllers;

import com.preacademy.second_evaluation.dtos.ProfessionalDTO;
import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.services.ProfessionalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    private static final Logger logger = LoggerFactory.getLogger(ProfessionalController.class);

    @Autowired
    private ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<?> createProfessional(@Valid @RequestBody ProfessionalDTO professionalDTO) {
        logger.info("Creando professional con id {}", professionalDTO.getId());
        Professional professional = new Professional();
        professional.setId(professionalDTO.getId());
        professional.setFullName(professionalDTO.getFullName());
        professional.setSpecialty(professionalDTO.getSpecialty());

        Professional savedProfessional  = professionalService.create(professional);
        professionalDTO.setId(savedProfessional.getId());
        return ResponseEntity.ok().body(professionalDTO);

    }

    @GetMapping
    public ResponseEntity<List<ProfessionalDTO>> getProfessionalsBySpecialty(@RequestParam(required = false) String specialty) {
        logger.info("Consultando todos los professionales con especialidad {}", specialty != null ? specialty : "all");
        List<Professional> professionals = specialty != null
        ? professionalService.getProfessionalBySpecialty(specialty)
                : professionalService.getAllProfessionals().values().stream().toList();

        List<ProfessionalDTO> professionalDTOS = professionals.stream()
                .map(p ->{
                    ProfessionalDTO professionalDTO = new ProfessionalDTO();
                    professionalDTO.setId(p.getId());
                    professionalDTO.setFullName(p.getFullName());
                    professionalDTO.setSpecialty(p.getSpecialty());
                    return professionalDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(professionalDTOS);
    }


}
