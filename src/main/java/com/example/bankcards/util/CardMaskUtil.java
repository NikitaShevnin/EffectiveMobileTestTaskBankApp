package com.example.bankcards.util;

/**
 * Утилита для маскирования номеров банковских карт.
 */
public class CardMaskUtil {
    public static String mask(String number) {
        if (number == null || number.length() < 4) {
            return number;
        }
        String last4 = number.substring(number.length() - 4);
        return "**** **** **** " + last4;
    }
}