package com.example.bankcards.dto;

import com.example.bankcards.entity.CardOperation;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CardOperationDTO(@NotEmpty String number,
                               String operation,
                               BigDecimal amount) {
}
