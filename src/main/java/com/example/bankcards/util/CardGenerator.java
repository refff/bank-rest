package com.example.bankcards.util;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CardGenerator {

    public static Card createCard(User user) {

        String expirationDate = LocalDate.now().plusYears(5).toString();
        String cardNumber = generateCardNumber();

        return Card.builder()
                .lastFourDigits(cardNumber)
                .cardHolder(user)
                .status(CardStatus.ACTIVE)
                .expirationDate(expirationDate)
                //.cardFingerprint()
                .balance(BigDecimal.valueOf(0.0)).build();
    }

    private static String generateCardNumber() {
        String numberId = "411111";
        String accountId = generateAccountId();
        String checkNumber = generateCheckNumber(accountId);

        return numberId+accountId+checkNumber;
    }

    private static String generateAccountId() {
        return "" + (int)((Math.random()*900000) + 100000000);
    }

    private static String generateCheckNumber(String accountId) {

        List<String> list = List.of(accountId.split(""));
        int doubledSum = 0;
        int sum = 0;

        for (int i = 7; i >= 0; i--) {
            int currentDigit = Integer.parseInt(list.get(i));
            int temp = 0;

            if (i % 2 != 0){
                temp += currentDigit * 2;

                if (temp > 9) {
                    temp -= 9;
                }

                doubledSum += temp;
            } else {
                sum += currentDigit;
            }
        }


        int totalSum = doubledSum + sum;

        int checkDigit = (totalSum % 10) == 0? 0 : 10 - totalSum % 10;

        return String.valueOf(checkDigit);
    }
}
