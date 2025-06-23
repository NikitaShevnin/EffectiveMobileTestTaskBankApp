package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEncodesPasswordAndPersistsUser() {
        User user = new User();
        user.setPassword("pass");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.save(user);

        verify(passwordEncoder).encode("pass");
        verify(userRepository).save(user);
        assertEquals("encoded", saved.getPassword());
    }

    @Test
    void findByUsernameReturnsFromRepository() {
        User user = new User();
        when(userRepository.findByUsername("u")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("u");

        verify(userRepository).findByUsername("u");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void updateDelegatesToSave() {
        User user = new User();
        user.setPassword("pass");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);

        User updated = userService.update(user);

        verify(userRepository).save(user);
        assertEquals("encoded", updated.getPassword());
    }

    @Test
    void deleteDelegatesToRepository() {
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void getCurrentUserReturnsUserWhenAuthenticated() {
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass"));

        Optional<User> result = userService.getCurrentUser();

        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUserReturnsEmptyWhenNoAuth() {
        SecurityContextHolder.clearContext();
        Optional<User> result = userService.getCurrentUser();
        assertTrue(result.isEmpty());
    }
}