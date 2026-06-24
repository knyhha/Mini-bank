package com.example.bank.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Email(message = "Invalid email format")
        @Size(min = 5, max = 254)
        String email,

        @NotBlank
        @Size(min = 1, max = 100)
        String firstName,

        @NotBlank
        @Size(min = 1, max = 100)
        String lastName,

        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Size(max = 128, message = "Password is too long")
        String password
) {
}
