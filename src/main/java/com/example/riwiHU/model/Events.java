package com.example.riwiHU.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    int id;
    String nombre;
    LocalDateTime fecha;
    String descripción;
}
