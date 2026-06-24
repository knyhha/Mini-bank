package com.example.bank.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotBlank
        String fromIban,

        @NotBlank
        String toIban,

        @NotBlank(message = "Amount is required")
        @Positive(message = "Transfer amount must be greater than zero")
        BigDecimal amount
) {
}
