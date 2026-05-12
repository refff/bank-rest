package com.example.bankcards.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CardOperationDTO(@NotEmpty String number,
                               @Min(value = 50, message = "Minimum amount is 50") BigDecimal amount) {
}
