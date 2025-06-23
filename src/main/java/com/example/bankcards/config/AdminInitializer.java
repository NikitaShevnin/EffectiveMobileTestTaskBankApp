package com.example.bankcards.config;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Создаёт администратора при старте приложения, используя учётные данные
 * из переменных окружения.
 */
@Component
public class AdminInitializer implements CommandLineRunner {
    private final UserService userService;

    @Value("${admin.username:}")
    private String username;

    @Value("${admin.password:}")
    private String password;

    public AdminInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (username == null || username.isBlank()) {
            return;
        }
        userService.findByUsername(username).ifPresentOrElse(user -> {
            if (!user.getRoles().contains(Role.ROLE_ADMIN)) {
                user.setRoles(Collections.singleton(Role.ROLE_ADMIN));
                userService.update(user);
            }
        }, () -> {
            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setRoles(Collections.singleton(Role.ROLE_ADMIN));
            userService.save(admin);
        });
    }
}