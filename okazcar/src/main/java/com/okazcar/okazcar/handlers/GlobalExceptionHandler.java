package com.okazcar.okazcar.handlers;

import jakarta.persistence.RollbackException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

import static itu.learn.learnfirebase.handler.ResponseHandler.showError;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransactionSystemException.class)
    public String handleConstraintViolation(TransactionSystemException ex) throws IOException {
        if (ex.getCause() instanceof RollbackException rollbackException) {
            if (rollbackException.getCause() != null) {
                // Handle the ConstraintViolationException here
                return showError(ex, HttpStatus.CONFLICT);
            }
        }

        // Handle other TransactionSystemExceptions here
        return showError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
