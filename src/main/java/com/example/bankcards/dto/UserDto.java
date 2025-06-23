package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO регистрации и обновления пользователя.
 */
public class UserDto {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public static UserDto fromEntity(com.example.bankcards.entity.User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}