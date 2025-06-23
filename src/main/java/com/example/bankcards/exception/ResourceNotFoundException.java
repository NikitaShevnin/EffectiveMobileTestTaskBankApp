package com.example.bankcards.exception;

/**
 * Исключение, выбрасываемое при отсутствии запрошенного ресурса в системе.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}