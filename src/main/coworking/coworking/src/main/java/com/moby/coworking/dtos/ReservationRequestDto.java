package com.moby.coworking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
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
