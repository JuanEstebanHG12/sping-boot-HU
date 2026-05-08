package com.example.riwiHU.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VenueRequest {
    @NotBlank(message = "The name is required")
    String nombre;

    String direction;
    int capacidad;
}
