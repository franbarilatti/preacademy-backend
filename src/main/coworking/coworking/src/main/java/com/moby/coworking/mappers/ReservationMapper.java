package com.moby.coworking.mappers;

import com.moby.coworking.models.Reservation;

public class ReservationMapper {

    public static ReservationResponseDto toDto(Reservation reservation){
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser().getId());
        dto.setRoomId(reservation.getRoom().getId());
        dto.setStatus(reservation.getStatus().name());
        dto.setStarDateTime(reservation.getStartDateTime());
        dto.setEndDateTime(reservation.getEndDateTime());
        return dto;
    }

}
