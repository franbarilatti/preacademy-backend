package com.moby.coworking.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDateTime starDateTime;
    private LocalDateTime endDateTime;
    private String status;

}
