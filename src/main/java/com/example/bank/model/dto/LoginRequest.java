package com.example.bank.model.dto;

public record LoginRequest(
        String email,
        String password
) {
}
