package com.example.bankcards.exception.CardExceptions;

public class NoSuchCardException extends RuntimeException {
    public NoSuchCardException(String data) {
        super(data);
    }
}
