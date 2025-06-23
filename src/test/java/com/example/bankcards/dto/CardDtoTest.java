package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CardDtoTest {
    @Test
    void fromEntityMasksNumber() {
        Card card = new Card();
        card.setId(1L);
        card.setNumber("1234567812345678");
        card.setExpiry("12/34");
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.TEN);

        CardDto dto = CardDto.fromEntity(card);
        assertEquals("**** **** **** 5678", dto.getNumber());
    }
}