package com.example.bankcards.entity.response;

import java.math.BigDecimal;

public record ATMResponseData (String cardNumber, String status, BigDecimal amount){
}
