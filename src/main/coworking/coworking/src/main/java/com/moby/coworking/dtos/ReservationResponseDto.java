package com.moby.coworking.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ReservationResponseDto {

    private Long id;

    private Long userId;

    private Long roomId;
    private LocalDateTime starDateTime;
    private LocalDateTime endDateTime;
    private String status;

}
