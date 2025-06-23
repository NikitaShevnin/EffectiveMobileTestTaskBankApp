package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.InvalidCardStatusException;
import com.example.bankcards.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDelegatesToRepository() {
        Card card = new Card();
        when(cardRepository.save(card)).thenReturn(card);

        Card saved = cardService.save(card);

        verify(cardRepository).save(card);
        assertEquals(card, saved);
    }

    @Test
    void findAllReturnsListFromRepository() {
        List<Card> cards = Arrays.asList(new Card(), new Card());
        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> result = cardService.findAll();

        verify(cardRepository).findAll();
        assertEquals(cards, result);
    }

    @Test
    void blockCardSetsStatusAndPersists() {
        Card card = new Card();
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.blockCard(card);

        assertEquals(CardStatus.BLOCKED, card.getStatus());
        verify(cardRepository).save(card);
        assertEquals(card, result);
    }

    @Test
    void depositIncreasesBalanceAndPersists() {
        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.valueOf(10));
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.deposit(card, BigDecimal.valueOf(5));

        assertEquals(BigDecimal.valueOf(15), card.getBalance());
        verify(cardRepository).save(card);
        assertEquals(card, result);
    }

    @Test
    void withdrawDecreasesBalanceAndPersists() {
        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.valueOf(10));
        when(cardRepository.save(card)).thenReturn(card);

        Card result = cardService.withdraw(card, BigDecimal.valueOf(3));

        assertEquals(BigDecimal.valueOf(7), card.getBalance());
        verify(cardRepository).save(card);
        assertEquals(card, result);
    }

    @Test
    void transferMovesMoneyBetweenCards() {
        Card from = new Card();
        from.setStatus(CardStatus.ACTIVE);
        from.setBalance(BigDecimal.valueOf(20));
        Card to = new Card();
        to.setStatus(CardStatus.ACTIVE);
        to.setBalance(BigDecimal.valueOf(5));

        cardService.transfer(from, to, BigDecimal.valueOf(10));

        assertEquals(BigDecimal.valueOf(10), from.getBalance());
        assertEquals(BigDecimal.valueOf(15), to.getBalance());
        verify(cardRepository).save(from);
        verify(cardRepository).save(to);
    }

    @Test
    void depositThrowsWhenCardNotActive() {
        Card card = new Card();
        card.setStatus(CardStatus.BLOCKED);

        assertThrows(InvalidCardStatusException.class,
                () -> cardService.deposit(card, BigDecimal.ONE));
        verify(cardRepository, never()).save(any());
    }

    @Test
    void withdrawThrowsForInsufficientFunds() {
        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.ONE);

        assertThrows(InsufficientFundsException.class,
                () -> cardService.withdraw(card, BigDecimal.valueOf(5)));
        verify(cardRepository, never()).save(any());
    }

    @Test
    void findByOwnerUsesSearchWhenProvided() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Card> page = new PageImpl<>(List.of(new Card()));
        when(cardRepository.findByOwnerUsernameAndNumberContaining("u", "123", pageable))
                .thenReturn(page);

        Page<Card> result = cardService.findByOwner("u", pageable, "123");

        verify(cardRepository).findByOwnerUsernameAndNumberContaining("u", "123", pageable);
        assertEquals(page, result);
    }

    @Test
    void findByOwnerWithoutSearchDelegatesSimpleQuery() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Card> page = new PageImpl<>(List.of(new Card()));
        when(cardRepository.findByOwnerUsername("u", pageable))
                .thenReturn(page);

        Page<Card> result = cardService.findByOwner("u", pageable, null);

        verify(cardRepository).findByOwnerUsername("u", pageable);
        assertEquals(page, result);
    }
}