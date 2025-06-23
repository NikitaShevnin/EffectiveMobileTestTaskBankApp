package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Сервис, помечающий карты как просроченные, когда истёк их срок действия.
 */
@Component
public class CardExpiryService {
    private final CardRepository cardRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

    public CardExpiryService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void expireCards() {
        List<Card> cards = cardRepository.findAll();
        YearMonth now = YearMonth.now();
        for (Card card : cards) {
            if (card.getStatus() != CardStatus.EXPIRED) {
                YearMonth expiry = YearMonth.parse(card.getExpiry(), formatter);
                if (!expiry.isAfter(now)) {
                    card.setStatus(CardStatus.EXPIRED);
                    cardRepository.save(card);
                }
            }
        }
    }
}