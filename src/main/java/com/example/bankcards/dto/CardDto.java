package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.util.CardMaskUtil;

import java.math.BigDecimal;

/**
 * DTO для сущности {@link com.example.bankcards.entity.Card}.
 */
public class CardDto {
    private Long id;
    private String number;
    private String expiry;
    private String status;
    private BigDecimal balance;

    public static CardDto fromEntity(Card card) {
        CardDto dto = new CardDto();
        dto.setId(card.getId());
        dto.setNumber(CardMaskUtil.mask(card.getNumber()));
        dto.setExpiry(card.getExpiry());
        dto.setStatus(card.getStatus().name());
        dto.setBalance(card.getBalance());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}