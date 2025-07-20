package com.moby.coworking.services;

import com.moby.coworking.dtos.ReservationRequestDto;
import com.moby.coworking.dtos.ReservationResponseDto;

import java.util.List;

public interface ReservationService {

    ReservationResponseDto createReservation(ReservationRequestDto dto);

    List<ReservationResponseDto> getAllReservations();

    List<ReservationResponseDto> getReservationByUser(Long userId);
    void cancelReservation(Long reservationId, Long currentUserId,boolean isAdmin);
}
