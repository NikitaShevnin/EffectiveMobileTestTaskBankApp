package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;

import java.math.BigDecimal;

/**
 * DTO that exposes full card information without masking.
 */
public class CardFullDto {
    private Long id;
    private String number;
    private String owner;
    private String expiry;
    private String status;
    private BigDecimal balance;

    public static CardFullDto fromEntity(Card card) {
        CardFullDto dto = new CardFullDto();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        if (card.getOwner() != null) {
            dto.setOwner(card.getOwner().getUsername());
        }
        dto.setExpiry(card.getExpiry());
        if (card.getStatus() != null) {
            dto.setStatus(card.getStatus().name());
        }
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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