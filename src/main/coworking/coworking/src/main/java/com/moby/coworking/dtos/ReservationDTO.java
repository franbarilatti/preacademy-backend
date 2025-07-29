package com.moby.coworking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
public class ReservationDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

}
