package com.example.bank.model.dto;

import java.time.Instant;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        Instant createdAt
) {}