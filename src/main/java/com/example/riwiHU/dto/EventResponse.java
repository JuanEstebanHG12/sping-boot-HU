package com.example.riwiHU.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    int id;
    String nombre;
    LocalDateTime fecha;
    String description;
}
