package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

public record CardRequest(@NotEmpty String number,
                          @NotEmpty String action){}
