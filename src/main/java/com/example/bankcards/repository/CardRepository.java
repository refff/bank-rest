package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCardNumber(String number);
    @Query(value = """
            select
            cards.id,
            cards.user_id,
            cards.status,
            cards.card_number,
            cards.expiration_date,
            cards.balance,
            users.username
            from cards
            left join users on users.id = cards.user_id
            where users.username = :username""", nativeQuery = true)
    Page<Card> findAllByUsername(@Param("username") String username, Pageable pageable);
}
