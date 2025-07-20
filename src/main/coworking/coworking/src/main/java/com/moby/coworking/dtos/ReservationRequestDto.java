package com.moby.coworking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ReservationRequestDto {

    @NotNull
    private Long userId;
    @NotNull
    private Long roomId;
    @NotNull
    private LocalDateTime startDatetime;
    @NotNull
    private LocalDateTime endDateTime;

}
