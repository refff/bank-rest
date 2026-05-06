package com.example.bankcards.dto;

import com.example.bankcards.entity.enums.CardAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CardOperationDTO(@NotEmpty String number,
                               @NotBlank CardAction operation,
                               @NotBlank BigDecimal amount) {
}
