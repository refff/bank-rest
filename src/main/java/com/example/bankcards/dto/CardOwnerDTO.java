package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

public class CardOwnerDTO {

    @NotEmpty
    private String owner;

    public CardOwnerDTO() {
    }

    public CardOwnerDTO(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public CardOwnerDTO setOwner(String owner) {
        this.owner = owner;
        return this;
    }
}
