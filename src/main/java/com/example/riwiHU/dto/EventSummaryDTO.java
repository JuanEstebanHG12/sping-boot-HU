package com.example.riwiHU.dto;

import java.time.LocalDateTime;

public record EventSummaryDTO(
    int id,
    String nombre,
    LocalDateTime fecha,
    String venueName,
    String city
) {}
