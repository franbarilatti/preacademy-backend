package com.preacademy.second_evaluation.repositories;

import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.models.Professional;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProfessionalRepository {

    private final Map<Long, Professional> professionals =  new HashMap<>();

    public Professional save(Professional professional) {
        professionals.put(professional.getId(), professional);
        return professional;
    }

    public List<Professional> findBySpecialty(String specialty){
        return professionals.values().stream()
                .filter(p -> p.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());
    }

    public Optional<Professional> findById(Long id) {
        return Optional.ofNullable(professionals.get(id));
    }

    public Map<Long, Professional> findAll(){
        return new HashMap<>(professionals);
    }

}
