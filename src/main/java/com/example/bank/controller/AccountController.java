package com.example.bank.controller;

import com.example.bank.model.dto.AccountResponse;
import com.example.bank.model.dto.CreateAccountRequest;
import com.example.bank.model.dto.SessionData;
import com.example.bank.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> all(
            @AuthenticationPrincipal SessionData session
    ) {
        return accountService.getAccountsByUserId(session.id());
    }

    @PostMapping
    public ResponseEntity<AccountResponse> addAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest,
            @AuthenticationPrincipal SessionData session) {
        return new ResponseEntity<>(accountService.createAccount(session.id(), createAccountRequest), HttpStatus.CREATED);
    }
}
