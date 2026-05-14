package com.example.bankcards.service;

import com.example.bankcards.dto.CardOwnerDTO;
import com.example.bankcards.entity.*;
import com.example.bankcards.entity.enums.BlockRequestStatus;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.enums.Roles;
import com.example.bankcards.entity.response.BlockAllCardsResponseData;
import com.example.bankcards.entity.response.CardOperationResponseData;
import com.example.bankcards.exception.CardExceptions.NoSuchCardException;
import com.example.bankcards.repository.BlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CardRepository cardRepository;
    private final BlockRequestRepository requestRepository;
    public static final String CARD_TEMPLATE = "**** **** **** ";

    @Transactional
    public ResponseEntity<?> grantAdminRoleToCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Role admin = roleRepository.findById(7).orElseThrow();

        user.setRoles(Set.of(admin));

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
    public CardOperationResponseData createCard(CardOwnerDTO cardOwnerDTO) {
        User owner = userRepository.findByUsername(cardOwnerDTO.getOwner())
                .orElseThrow(() -> new RuntimeException("No such user"));


        //todo move to separate class
        String expirationDate = LocalDate.now().plusYears(5).toString();
        String cardNumber = CARD_TEMPLATE + (int)((Math.random()*9000) + 1000);

        Card card = new Card()
                .setCardNumber(cardNumber)
                .setOwner(owner)
                .setStatus(CardStatus.ACTIVE)
                .setExpirationDate(expirationDate)
                .setBalance(BigDecimal.valueOf(0.0));
        //

        cardRepository.save(card);

        return new CardOperationResponseData(
                CARD_TEMPLATE+cardNumber,
                "Card created");
    }

    @Transactional
    public CardOperationResponseData updateCardStatus(String cardNumber, CardStatus status) {
        Card card = cardRepository.findByCardNumber(CARD_TEMPLATE + cardNumber).orElseThrow();

        switch (status) {
            case ACTIVE -> card.setStatus(CardStatus.ACTIVE);
            case LOCKED -> card.setStatus(CardStatus.LOCKED);
        }

        cardRepository.save(card);

        return new CardOperationResponseData(
                CARD_TEMPLATE+number,
                "Card is " + status);
    }

    @Transactional
    public CardOperationResponseData deleteCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(CARD_TEMPLATE + cardNumber)
                .orElseThrow(() -> new NoSuchCardException(cardNumber));

        cardRepository.delete(card);

        return new CardOperationResponseData(
                CARD_TEMPLATE + number,
                "Card is deleted");
    }

    @Transactional
    public List<BlockRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    public BlockAllCardsResponseData blockAll() {
        requestRepository.findAll()
                .forEach(request ->
                {
                    request.getCard().setStatus(CardStatus.LOCKED);
                    request.setStatus(BlockRequestStatus.APPROVED);
                });

        return new BlockAllCardsResponseData("All cards from the block queue have been blocked");
    }

}
