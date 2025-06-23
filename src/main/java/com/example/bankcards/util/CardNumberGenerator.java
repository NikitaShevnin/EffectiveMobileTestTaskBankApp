package com.example.bankcards.util;

import java.security.SecureRandom;

/**
 * Утилита для генерации случайных номеров карт.
 */
public class CardNumberGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}