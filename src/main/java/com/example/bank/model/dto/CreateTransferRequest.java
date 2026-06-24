package com.example.bank.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotBlank
        @Pattern(regexp = "\\d{16}", message = "The card number must consist of 16 digits")
        String fromCardNumber,

        @NotBlank
        @Pattern(regexp = "\\d{16}", message = "The card number must consist of 16 digits")
        String toCardNumber,

        @NotBlank(message = "Amount is required")
        @Positive(message = "Transfer amount must be greater than zero")
        BigDecimal amount
) {
}
