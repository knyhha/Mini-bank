package com.example.bank.controller;

import com.example.bank.model.dto.CreateTransferRequest;
import com.example.bank.model.dto.SessionData;
import com.example.bank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer (
            @RequestBody CreateTransferRequest createTransferRequest,
            @AuthenticationPrincipal SessionData session) {
        transactionService.transfer(session.id(), createTransferRequest);

        // TODO status response
        return null;
    }
}
