package com.example.bank.controller;

import com.example.bank.model.dto.CreateUserRequest;
import com.example.bank.model.dto.UserResponse;
import com.example.bank.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(authService.createUser(createUserRequest), HttpStatus.CREATED);
    }
}
