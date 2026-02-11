package com.example.common.exception;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(double balance, double required) {
        super(String.format("Insufficient balance. Available: %.2f, Required: %.2f", balance, required));
    }
}

