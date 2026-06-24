package com.example.bank.exception.transfer;

import com.example.bank.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends BaseException {
    public InsufficientFundsException() {
        super("Insufficient funds", "INSUFFICIENT_FUNDS", HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
