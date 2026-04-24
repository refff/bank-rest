package com.example.bankcards.exception.CardExceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("На карте недостаточно средств");
    }
}
