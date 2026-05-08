package com.example.riwiHU.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venue {
    int id;
    String nombre;
    String dirección;
    int capacidad;
}
