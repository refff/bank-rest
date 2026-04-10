package com.example.bankcards.dto;

import com.example.bankcards.entity.User;
import jakarta.validation.constraints.NotEmpty;

public class CardDTO {

    @NotEmpty
    private String owner;

    public CardDTO() {
    }

    public CardDTO(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public CardDTO setOwner(String owner) {
        this.owner = owner;
        return this;
    }
}
