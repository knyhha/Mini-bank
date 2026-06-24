package com.example.bank.exception.handler;

import com.example.bank.exception.base.BaseException;
import com.example.bank.model.dto.BaseExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handle(BaseException ex) {
        return new ResponseEntity<>(new BaseExceptionResponse(ex.getErrorCode(), ex.getMessage()), ex.getStatus());
    }
}
