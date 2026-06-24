package com.example.bank.model.dto;

import com.example.bank.enums.Currency;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull
        Currency currency
) {
}
