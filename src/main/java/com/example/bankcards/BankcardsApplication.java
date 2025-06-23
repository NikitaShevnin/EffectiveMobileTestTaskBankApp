package com.example.bankcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Точка входа в Spring Boot приложение Bankcards.
 */
@SpringBootApplication
@EnableScheduling
public class BankcardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankcardsApplication.class, args);
    }
}