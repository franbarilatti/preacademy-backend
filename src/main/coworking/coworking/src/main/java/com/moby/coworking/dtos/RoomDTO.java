package com.moby.coworking.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomDTO {

    @NotBlank
    private String name;

    @Min(1)
    private int capacity;

    @NotBlank
    private String location;

    private boolean enabled;










}
