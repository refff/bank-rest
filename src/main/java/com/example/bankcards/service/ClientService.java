package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService {

    private final CardRepository cardRepository;

    public ClientService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> getClientCards() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Card> cardList = cardRepository.findAllByUsername(username);

        return new ResponseEntity<>(cardList, HttpStatus.OK);
    }


}
