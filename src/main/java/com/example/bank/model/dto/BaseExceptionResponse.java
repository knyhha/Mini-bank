package com.example.bank.model.dto;

public record BaseExceptionResponse(
        String errorCode,
        String message
) {
}
