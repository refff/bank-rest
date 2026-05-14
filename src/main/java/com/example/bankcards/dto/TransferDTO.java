package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record TransferDTO(@NotEmpty String fromCard,
                          @NotEmpty String toCard,
                          BigDecimal amount) {}


