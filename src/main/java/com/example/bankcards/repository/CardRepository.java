package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для выполнения CRUD-операций над сущностями {@link Card}.
 */

public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findByOwnerUsername(String username, Pageable pageable);

    Page<Card> findByOwnerUsernameAndNumberContaining(String username, String number, Pageable pageable);

    Optional<Card> findByIdAndOwnerUsername(Long id, String username);

    Optional<Card> findByNumberAndOwnerUsername(String number, String username);
}