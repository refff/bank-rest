package com.example.bankcards.entity;

public enum CardStatus {
    ACTIVE("Active".toUpperCase()),
    LOCKED("Locked".toUpperCase()),
    EXPIRED("Expired".toUpperCase());

    String name;

    CardStatus(String name) {
        this.name = name;
    }
}
