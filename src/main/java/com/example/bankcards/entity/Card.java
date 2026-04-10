package com.example.bankcards.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.scheduling.config.TaskExecutionOutcome;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String cardNumber;

    private String expirationDate;

    private CardStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    private Double balance;

    public Card() {
    }

    public Card(int id, String cardNumber, String expirationDate, CardStatus status, User owner) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.status = status;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public Card setId(int id) {
        this.id = id;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Card setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public Card setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public CardStatus getStatus() {
        return status;
    }

    public Card setStatus(CardStatus status) {
        this.status = status;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Card setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public Card setBalance(Double balance) {
        this.balance = balance;
        return this;
    }
}
