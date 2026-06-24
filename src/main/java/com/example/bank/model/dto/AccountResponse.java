package com.example.bank.model.dto;

import com.example.bank.enums.Currency;
import com.example.bank.model.entity.Account;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
    Long id,
    String accountNumber,
    Currency currency,
    BigDecimal balance,
    Instant createdAt
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCurrency(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}
