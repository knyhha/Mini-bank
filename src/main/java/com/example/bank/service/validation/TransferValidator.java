package com.example.bank.service.validation;

import com.example.bank.enums.AccountStatus;
import com.example.bank.exception.transfer.InsufficientFundsException;
import com.example.bank.exception.transfer.SelfTransferException;
import com.example.bank.model.entity.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferValidator {
    public void validate(Account from, Account to, BigDecimal amount) {
        if (from.getId().equals(to.getId())) {
            throw new SelfTransferException();
        }

        if (from.getStatus() != AccountStatus.ACTIVE) {
            // TODO
            throw new IllegalStateException("Account not active");
        }

        if (to.getStatus() != AccountStatus.ACTIVE) {
            // TODO
            throw new IllegalStateException("Account not active");
        }

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
    }
}
