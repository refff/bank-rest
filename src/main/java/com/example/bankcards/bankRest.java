package com.example.bankcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
class BankCards {
    public static void main(String[] args) {
        SpringApplication.run(BankCards.class, args);
    }
}