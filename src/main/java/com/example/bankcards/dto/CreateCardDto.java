package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO для создания карты администратором.
 */
public class CreateCardDto {
    @NotNull
    private Long ownerId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}