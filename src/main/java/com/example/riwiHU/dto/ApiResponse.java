package com.example.riwiHU.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
}
