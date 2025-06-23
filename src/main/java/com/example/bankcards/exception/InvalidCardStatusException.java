package com.example.bankcards.exception;

/**
 * Выбрасывается, когда статус карты не позволяет выполнить запрошенную операцию.
 */
public class InvalidCardStatusException extends RuntimeException {
    public InvalidCardStatusException(String message) {
        super(message);
    }
}