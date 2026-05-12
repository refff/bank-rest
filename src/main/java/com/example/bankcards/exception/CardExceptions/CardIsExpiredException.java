package com.example.bankcards.exception.CardExceptions;

public class CardIsExpiredException extends RuntimeException {
    public CardIsExpiredException(String message) {
        super(message);
    }
}
