package com.example.bankcards.entity;

public enum CardStatus {
    ACTIVE("Active"),
    LOCKED("Locked"),
    EXPIRED("Expired");

    CardStatus(String name) {
    }
}
