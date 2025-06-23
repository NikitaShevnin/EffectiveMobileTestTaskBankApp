package com.example.bankcards.exception;

/**
 * Выбрасывается, когда на карте недостаточно средств для выполнения операции.
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}