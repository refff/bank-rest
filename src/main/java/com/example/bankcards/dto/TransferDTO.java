package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

//todo move cardnumber to int type
public record TransferDTO(@NotEmpty String fromCard,
                          @NotEmpty String toCard,
                          BigDecimal amount) {}


