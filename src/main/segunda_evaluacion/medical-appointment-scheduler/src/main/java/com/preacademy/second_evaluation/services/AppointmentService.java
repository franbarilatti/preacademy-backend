package com.preacademy.second_evaluation.services;

import com.preacademy.second_evaluation.exceptions.InvalidDataException;
import com.preacademy.second_evaluation.exceptions.ResourceNotFoundException;
import com.preacademy.second_evaluation.models.Appointment;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.repositories.AppointmentRepository;
import com.preacademy.second_evaluation.repositories.PatientRepository;
import com.preacademy.second_evaluation.repositories.ProfessionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository aappointmentRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    public Appointment createAppointment(Appointment appointment){
        logger.info("Creando turno con ID {}, ID de paciente: {}, ID del profesional: {}, fecha: {}", appointment.getId(), appointment.getPatient().getId(), appointment.getProfessional().getId(),  appointment.getDate());

        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> {
                    logger.error("Paciente no encontrado con la id: {}", appointment.getPatient().getId());
                    return new ResourceNotFoundException("Paciente no encontrado con la id: " + appointment.getPatient().getId() );
                });

        Professional professional = professionalRepository.findById(appointment.getProfessional().getId())
                .orElseThrow(() -> {
                    logger.error("Profesional no encontrado con la id: {}", appointment.getProfessional().getId());
                    return new ResourceNotFoundException("Profesional no encontrado con la id: " + appointment.getProfessional().getId() );
                });

        boolean exists = appointmentRepository.findAll().values().stream()
                .anyMatch(a -> a.getPatient().getId().equals(appointment.getPatient().getId())
                && a.getProfessional().getId().equals(appointment.getProfessional().getId())
                && a.getDate().equals(appointment.getDate()));
        if(exists){
            logger.error("Se detecto un turno duplicado para el paciente: {} {}, profesional: {}, fecha: {}",
                    appointment.getPatient().getName(), appointment.getPatient().getLastName(), appointment.getProfessional().getFullName(), appointment.getDate());
            throw new InvalidDataException("Turno duplicado para el mismo paciente, profesional y fecha");
        }

        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        return aappointmentRepository.save(appointment);

    }

    public Map<Long, Appointment> getAllAppointments(){
        logger.info("Consultando todos los turnos");
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date){
        logger.info("Consultando todos los turnos por fecha: {}", date);
        return appointmentRepository.findByDate(date);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDate from, LocalDate to){
        if(from.isAfter(to)){
            logger.error("Rango de fechas invalido, fecha de inicio: {} es posterior a la fecha final: {}", from, to);
            new InvalidDataException("La fecha de inicio no puede ser posterior a la de finalizacion");
        }
        logger.info("Consultando todos los turnos de {} a {}", from, to);
        return appointmentRepository.findByDateRange(from, to);
    }

    public void deleteAppointment(Long id){
        logger.info("Eliminando turno con ID {}", id);
        if(!appointmentRepository.findById(id).isPresent()){
            logger.error("No existe el turno con ID: {}", id);
            throw new ResourceNotFoundException("No existe el turno con ID: " + id);
        }
        appointmentRepository.delete(id);
    }

}
