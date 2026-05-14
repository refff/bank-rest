package com.example.bankcards.entity;

import com.example.bankcards.dto.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Transfer {

    private Card fromCard;
    private Card toCard;
    private BigDecimal value;

}
