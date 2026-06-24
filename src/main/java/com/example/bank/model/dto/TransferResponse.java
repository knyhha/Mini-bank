package com.example.bank.model.dto;

import com.example.bank.enums.TransactionStatus;

public record TransferResponse(
        TransactionStatus status
) {
}
