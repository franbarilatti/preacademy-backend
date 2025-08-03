package com.preacademy.second_evaluation.services;

import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.repositories.ProfessionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfessionalService {

    private static final Logger logger = LoggerFactory.getLogger(ProfessionalService.class);

    @Autowired
    private ProfessionalRepository  professionalRepository;


    public Professional create(Professional professional) {
        logger.info("creando profesional con ID: {}", professional.getId());
        return professionalRepository.save(professional);
    }

    public List<Professional> getProfessionalBySpecialty(String specialty){
        logger.info("Consultando profesionales con especialidad: {}", specialty);
        return professionalRepository.findBySpecialty(specialty);
    }

    public Map<Long, Professional> getAllProfessionals(){
        logger.info("Consultando todos los profesionales");
        return professionalRepository.findAll();
    }
}
