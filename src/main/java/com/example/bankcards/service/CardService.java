package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.InvalidCardStatusException;
import com.example.bankcards.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import com.example.bankcards.util.CardNumberGenerator;

/**
 * Сервис для работы с карточками в базе данных.
 */
@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card save(Card card) {
        if (card.getNumber() == null || card.getNumber().isBlank()) {
            card.setNumber(CardNumberGenerator.generate());
        }
        if (card.getExpiry() == null || card.getExpiry().isBlank()) {
            YearMonth expiry = YearMonth.now().plusYears(1);
            card.setExpiry(expiry.format(DateTimeFormatter.ofPattern("MM/yy")));
        }
        if (card.getStatus() == null) {
            card.setStatus(CardStatus.ACTIVE);
        }
        if (card.getBalance() == null) {
            card.setBalance(BigDecimal.ZERO);
        }
        Card saved = cardRepository.save(card);
        return saved;
    }

    public List<Card> findAll() {
        List<Card> list = cardRepository.findAll();
        return list;
    }

    public Page<Card> findByOwner(String username, Pageable pageable, String search) {
        Page<Card> page;
        if (search == null || search.isBlank()) {
            page = cardRepository.findByOwnerUsername(username, pageable);
        } else {
            page = cardRepository.findByOwnerUsernameAndNumberContaining(username, search, pageable);
        }
        return page;
    }

    public Optional<Card> findByIdAndOwner(Long id, String username) {
        Optional<Card> card = cardRepository.findByIdAndOwnerUsername(id, username);
        return card;
    }

    public Optional<Card> findById(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        return card;
    }

    public Optional<Card> findByNumberAndOwner(String number, String username) {
        Optional<Card> card = cardRepository.findByNumberAndOwnerUsername(number, username);
        return card;
    }

    @Transactional
    public Card blockCard(Card card) {
        card.setStatus(CardStatus.BLOCKED);
        Card saved = cardRepository.save(card);
        return saved;
    }

    @Transactional
    public Card activateCard(Card card) {
        card.setStatus(CardStatus.ACTIVE);
        Card saved = cardRepository.save(card);
        return saved;
    }

    @Transactional
    public Card deposit(Card card, BigDecimal amount) {
        validateActive(card);
        card.setBalance(card.getBalance().add(amount));
        Card saved = cardRepository.save(card);
        return saved;
    }

    @Transactional
    public Card withdraw(Card card, BigDecimal amount) {
        validateActive(card);
        if (card.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств");
        }
        card.setBalance(card.getBalance().subtract(amount));
        Card saved = cardRepository.save(card);
        return saved;
    }

    @Transactional
    public void transfer(Card from, Card to, BigDecimal amount) {
        validateActive(from);
        validateActive(to);
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств");
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        cardRepository.save(from);
        cardRepository.save(to);
    }

    @Transactional
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    private void validateActive(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new InvalidCardStatusException("Карта не активна");
        }
    }
}