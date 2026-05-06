package com.example.bankcards.dto;

import com.example.bankcards.entity.enums.CardStatus;
import jakarta.validation.constraints.NotEmpty;

public record CardStatusRequest(@NotEmpty String number,
                                CardStatus action){}
