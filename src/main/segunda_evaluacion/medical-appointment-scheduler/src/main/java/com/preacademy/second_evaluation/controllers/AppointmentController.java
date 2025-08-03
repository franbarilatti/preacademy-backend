package com.preacademy.second_evaluation.controllers;


import com.preacademy.second_evaluation.dtos.AppointmentDTO;
import com.preacademy.second_evaluation.exceptions.ResourceNotFoundException;
import com.preacademy.second_evaluation.models.Appointment;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.services.AppointmentService;
import com.preacademy.second_evaluation.services.PatientService;
import com.preacademy.second_evaluation.services.ProfessionalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private PatientService patientService;


    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO){

        logger.info("Creando un turno con ID de paciente: {}, ID de profesional y fecha: {}", appointmentDTO.getPatientId(), appointmentDTO.getProfessionalId(), appointmentDTO.getDate());

        Patient patient = patientService.getPatientById(appointmentDTO.getPatientId());
        Professional professional = professionalService.getAllProfessionals().get(appointmentDTO.getProfessionalId());

        if(professional == null){
            logger.error("Profesional no encontrado con ID: {}", appointmentDTO.getProfessionalId());
            throw new ResourceNotFoundException("Profesional no encontrado");
        }

        Appointment appointment = new Appointment();
        appointment.setId(appointmentDTO.getId());
        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        appointment.setDate(appointmentDTO.getDate());

        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        appointment.setId(savedAppointment.getId());
        return ResponseEntity.ok().body(appointmentDTO);

    }

    @GetMapping
    public ResponseEntity<Map<Long, AppointmentDTO>> getAllAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){

        logger.info("Consultando turnos desde: {} hasta: {}", from, to);
        List<Appointment> appointments = (from != null && to != null)
                ? appointmentService.getAppointmentsByDateRange(from, to)
                : appointmentService.getAllAppointments().values().stream().toList();

        Map<Long, AppointmentDTO> appointmentDTOMap = appointments.stream()
                .collect(Collectors.toMap(
                        Appointment::getId,
                        a -> {
                            AppointmentDTO appointmentDTO = new AppointmentDTO();
                            appointmentDTO.setId(a.getId());
                            appointmentDTO.setPatientId(a.getPatient().getId());
                            appointmentDTO.setProfessionalId(a.getProfessional().getId());
                            appointmentDTO.setDate(a.getDate());
                            return appointmentDTO;
                        }
                ));

        return ResponseEntity.ok(appointmentDTOMap);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        logger.info("Consultando turnos para la fecha: {}", date);
        List<AppointmentDTO> appointmentDTOS = appointmentService.getAppointmentsByDate(date).stream()
                .map(a -> {
                    AppointmentDTO appointmentDTO = new AppointmentDTO();
                    appointmentDTO.setId(a.getId());
                    appointmentDTO.setPatientId(a.getPatient().getId());
                    appointmentDTO.setProfessionalId(a.getProfessional().getId());
                    appointmentDTO.setDate(a.getDate());
                    return appointmentDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id){
        logger.info("Eliminando un turno con ID: {}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}
