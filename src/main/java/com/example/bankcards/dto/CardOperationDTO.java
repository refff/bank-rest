package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CardOperationDTO(@NotEmpty String number,
                               @NotEmpty String operation,
                               BigDecimal amount) {
}
