package com.example.riwiHU.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {
    @NotBlank(message = "The name is required")
    String nombre;
    String description;
    LocalDateTime fecha;
    Integer venueId;
    List<Integer> categoryIds;
}
