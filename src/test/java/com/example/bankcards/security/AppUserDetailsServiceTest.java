package com.example.bankcards.security;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AppUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameReturnsUserDetails() {
        User user = User.builder()
                .username("u")
                .password("p")
                .roles(Collections.singleton(Role.ROLE_USER))
                .build();
        when(userService.findByUsername("u")).thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("u");

        assertEquals("u", details.getUsername());
        assertEquals("p", details.getPassword());
        assertEquals(1, details.getAuthorities().size());
    }

    @Test
    void loadUserByUsernameThrowsWhenNotFound() {
        when(userService.findByUsername("none")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("none"));
    }
}