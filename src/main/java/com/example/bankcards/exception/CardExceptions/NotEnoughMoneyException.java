package com.example.bankcards.exception.CardExceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Not enough money on balance");
    }
}
