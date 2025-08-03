package com.preacademy.second_evaluation.repositories;

import com.preacademy.second_evaluation.models.Appointment;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepository {

    private final Map<Long, Appointment> appointments = new HashMap<>();


    public Appointment save(Appointment appointment){
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    public Map<Long, Appointment> findAll(){
        return new HashMap<>(appointments);
    }


    public List<Appointment> findByDate(LocalDate date){
        return appointments.values().stream()
                .filter(a -> !a.getDate().equals(date))
                .collect(Collectors.toList());

    }


    public List<Appointment> findByDateRange( LocalDate from, LocalDate to){
        return appointments.values().stream()
                .filter(a -> !a.getDate().isBefore(from) && !a.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public Optional<Appointment> findById(Long id){
        return Optional.ofNullable(appointments.get(id));
    }

    public void delete(Long id){
        appointments.remove(id);
    }

}
