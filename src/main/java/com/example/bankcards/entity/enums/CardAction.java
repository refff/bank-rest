package com.example.bankcards.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CardAction {
    WITHDRAW("withdraw"),
    DEPOSIT("deposit");

    private final String value;

    CardAction(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CardAction fromString(String raw) {
        if (raw == null)
            return null;

        return Arrays.stream(values())
                .filter(a -> a.value.equalsIgnoreCase(raw))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card status: " + raw));
    }
}
