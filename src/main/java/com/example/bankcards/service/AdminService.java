package com.example.bankcards.service;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.*;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CardRepository cardRepository;
    public static final String cardTemplate = "**** **** **** ";

    @Autowired
    public AdminService(UserRepository userRepository,
                        RoleRepository roleRepository,
                        CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public ResponseEntity<?> getAdminAccess() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).get();
        Role admin = roleRepository.findById(7).get();

        user.setRoles(admin);

        userRepository.save(user);

        return new ResponseEntity<>(Map.of(
               "subject", username,
               "status", Roles.ADMIN.getRoleName()
               ), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    //todo save full card number to db and return masked
    @Transactional
    public ResponseEntity<?> createCard(CardDTO cardDTO) {
        User owner = userRepository.findByUsername(cardDTO.getOwner())
                .orElseThrow(() -> new RuntimeException("No such user"));

        String expirationDate = LocalDate.now().plusYears(5).toString();
        String cardNumber = cardTemplate + (int)((Math.random()*9000) + 1000);

        Card card = new Card()
                .setCardNumber(cardNumber)
                .setOwner(owner)
                .setStatus(CardStatus.ACTIVE)
                .setExpirationDate(expirationDate)
                .setBalance(BigDecimal.valueOf(0.0));

        cardRepository.save(card);

        return new ResponseEntity<>(Map.of(
                "status", "Card created",
                "number", cardNumber), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> processCardAction(String number, String status) {
        Card card = cardRepository.findByCardNumber(cardTemplate + number).get();

        switch (status) {
            case "ACTIVATE" -> card.setStatus(CardStatus.ACTIVE);
            case "LOCK" -> card.setStatus(CardStatus.LOCKED);
        }

        cardRepository.save(card);

        return new ResponseEntity<>(Map.of(
                "status", "Card is " + status,
                "card", cardTemplate + number), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?>  deleteCard(String number) {
        Card card = cardRepository.findByCardNumber(cardTemplate + number).get();

        cardRepository.delete(card);

        return new ResponseEntity<>(Map.of(
                "status", "Card is deleted",
                "card", cardTemplate + number), HttpStatus.OK);
    }

}
