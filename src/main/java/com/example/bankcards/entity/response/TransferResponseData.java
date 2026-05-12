package com.example.bankcards.entity.response;

import java.math.BigDecimal;

public record TransferResponseData(String status, BigDecimal amount) {
}
