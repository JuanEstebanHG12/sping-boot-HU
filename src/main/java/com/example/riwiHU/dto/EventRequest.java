package com.example.riwiHU.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    @NotBlank(message = "The name is required")
    String nombre;
    String description;
}
