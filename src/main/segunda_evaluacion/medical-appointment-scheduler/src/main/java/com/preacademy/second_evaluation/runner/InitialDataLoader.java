package com.preacademy.second_evaluation.runner;

import com.preacademy.second_evaluation.models.Appointment;
import com.preacademy.second_evaluation.models.Patient;
import com.preacademy.second_evaluation.models.Professional;
import com.preacademy.second_evaluation.services.AppointmentService;
import com.preacademy.second_evaluation.services.PatientService;
import com.preacademy.second_evaluation.services.ProfessionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private AppointmentService appointmentService;


    @Override
    public void run(String... args) {
        logger.info("Loading initial data...");

        // Create patients
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("John");
        patient1.setLastName("Doe");
        patient1.setDni("12345678");
        patient1.setEmail("john.doe@example.com");
        patientService.createPatient(patient1);
        logger.info("Created patient: {}", patient1);

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Jane");
        patient2.setLastName("Smith");
        patient2.setDni("87654321");
        patient2.setEmail("jane.smith@example.com");
        patientService.createPatient(patient2);
        logger.info("Created patient: {}", patient2);

        // Create professionals
        Professional professional1 = new Professional();
        professional1.setId(1L);
        professional1.setFullName("Dr. Alice Brown");
        professional1.setSpecialty("Clínica");
        professionalService.create(professional1);
        logger.info("Created professional: {}", professional1);

        Professional professional2 = new Professional();
        professional2.setId(2L);
        professional2.setFullName("Dr. Bob Wilson");
        professional2.setSpecialty("Odontología");
        professionalService.create(professional2);
        logger.info("Created professional: {}", professional2);

        // Create appointments
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setPatient(patient1);
        appointment1.setProfessional(professional1);
        appointment1.setDate(LocalDate.of(2025, 8, 10));
        appointmentService.createAppointment(appointment1);
        logger.info("Created appointment: {}", appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setPatient(patient2);
        appointment2.setProfessional(professional1);
        appointment2.setDate(LocalDate.of(2025, 8, 11));
        appointmentService.createAppointment(appointment2);
        logger.info("Created appointment: {}", appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setId(3L);
        appointment3.setPatient(patient1);
        appointment3.setProfessional(professional2);
        appointment3.setDate(LocalDate.of(2025, 8, 12));
        appointmentService.createAppointment(appointment3);
        logger.info("Created appointment: {}", appointment3);

        logger.info("Initial data loading completed.");
    }

}
