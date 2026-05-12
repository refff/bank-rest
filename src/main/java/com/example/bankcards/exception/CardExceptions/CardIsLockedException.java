package com.example.bankcards.exception.CardExceptions;

public class CardIsLockedException extends RuntimeException {
    public CardIsLockedException(String message) {
        super(message);
    }
}
