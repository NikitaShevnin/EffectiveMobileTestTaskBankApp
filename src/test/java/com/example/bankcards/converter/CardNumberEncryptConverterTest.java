package com.example.bankcards.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberEncryptConverterTest {

    @Test
    void encryptsAndDecrypts() {
        CardNumberEncryptConverter conv = new CardNumberEncryptConverter();
        String num = "1234567890123456";
        String enc = conv.convertToDatabaseColumn(num);
        assertNotEquals(num, enc);
        String dec = conv.convertToEntityAttribute(enc);
        assertEquals(num, dec);
    }
}