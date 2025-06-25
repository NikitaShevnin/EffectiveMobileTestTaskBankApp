package com.example.bankcards.controller;

import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.security.SecurityConfig;
import com.example.bankcards.security.JwtTokenProvider;
import com.example.bankcards.security.AppUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.Card;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
@Import(SecurityConfig.class)
class CardControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AppUserDetailsService appUserDetailsService;

    @Test
    @WithMockUser(roles = "USER")
    void allRequiresAdminRole() throws Exception {
        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createMyCardAllowedForUser() throws Exception {
        when(userService.getCurrentUser()).thenReturn(java.util.Optional.of(new User()));
        when(cardService.save(any(Card.class))).thenReturn(new Card());

        mockMvc.perform(post("/api/cards/createCard"))
                .andExpect(status().isOk());
    }
}