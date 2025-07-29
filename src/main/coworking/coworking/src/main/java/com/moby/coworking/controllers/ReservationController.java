package com.moby.coworking.controllers;

import com.moby.coworking.dtos.ReservationDTO;
import com.moby.coworking.models.Reservation;
import com.moby.coworking.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO){
        return  ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> getReservationByUser(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.getReservationByUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        reservationService.cancelReservation(id);

        return ResponseEntity.ok().build();
    }


}
