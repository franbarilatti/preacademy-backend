package com.moby.coworking.controllers;

import com.moby.coworking.dtos.ReservationRequestDto;
import com.moby.coworking.dtos.ReservationResponseDto;
import com.moby.coworking.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody ReservationRequestDto dto){
        ReservationResponseDto created = reservationService.createReservation(dto);
        return ResponseEntity.ok(created);

    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> listReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDto>> listUserReservations(@PathVariable Long userId){
        return ResponseEntity.ok(reservationService.getReservationByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id, @RequestParam Long currentUserid, @RequestParam boolean isAdmin){
        reservationService.cancelReservation(id,currentUserid,isAdmin);
        return ResponseEntity.noContent().build();
    }

}
