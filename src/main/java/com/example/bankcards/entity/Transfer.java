package com.example.bankcards.entity;

import com.example.bankcards.dto.TransferDTO;

import java.math.BigDecimal;

public class Transfer {

    private Card fromCard;
    private Card toCard;
    private BigDecimal value;

    public Transfer(Card fromCard, Card toCard, BigDecimal value) {
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.value = value;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public Transfer setFromCard(Card fromCard) {
        this.fromCard = fromCard;
        return this;
    }

    public Card getToCard() {
        return toCard;
    }

    public Transfer setToCard(Card toCard) {
        this.toCard = toCard;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Transfer setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}
