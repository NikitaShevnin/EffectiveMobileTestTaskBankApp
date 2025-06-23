package com.example.bankcards.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider provider;

    @BeforeEach
    void setUp() throws Exception {
        provider = new JwtTokenProvider();

        Field secret = JwtTokenProvider.class.getDeclaredField("secret");
        secret.setAccessible(true);
        secret.set(provider, "testsecret");

        Field expiration = JwtTokenProvider.class.getDeclaredField("expiration");
        expiration.setAccessible(true);
        expiration.set(provider, 3600000L);
    }

    @Test
    void generateAndParseToken() {
        UserDetails user = new User("user", "pass", Collections.emptyList());

        String token = provider.generateToken(user);
        assertNotNull(token);

        String username = provider.getUsername(token);
        assertEquals("user", username);
    }
}