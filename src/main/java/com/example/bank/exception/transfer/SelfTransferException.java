package com.example.bank.exception.transfer;

import com.example.bank.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class SelfTransferException extends BaseException {
    public SelfTransferException() {
        super("Self transfer not allowed", "SELF_TRANSFER", HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
