package com.example.bankcards.dto;

import jakarta.validation.constraints.NotEmpty;

public record CardStatusRequest(@NotEmpty String number,
                                @NotEmpty String action){}
