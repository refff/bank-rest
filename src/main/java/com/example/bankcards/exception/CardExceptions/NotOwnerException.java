package com.example.bankcards.exception.CardExceptions;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException() {
        super("Not owner of card");
    }
}
