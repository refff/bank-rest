package com.example.bankcards.service;

import com.example.bankcards.dto.CardOwnerDTO;
import com.example.bankcards.entity.*;
import com.example.bankcards.entity.enums.BlockRequestStatus;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.response.BlockAllCardsResponseData;
import com.example.bankcards.entity.response.CardOperationResponseData;
import com.example.bankcards.entity.response.CreateUserResponseData;
import com.example.bankcards.exception.CardExceptions.NoSuchCardException;
import com.example.bankcards.repository.BlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardFormatter;
import com.example.bankcards.util.CardGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
    public CreateUserResponseData grantAdminRoleToCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Role admin = roleRepository.findById(7).orElseThrow();

        user.setRoles(Set.of(admin));

        userRepository.save(user);

        return new CreateUserResponseData(
                user.getUsername(),
                "Admin role granted!");
    }

    @Transactional(readOnly = true)
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional
    public CardOperationResponseData createCard(CardOwnerDTO cardOwnerDTO) {
        User user = userRepository.findByUsername(cardOwnerDTO.getOwner())
                .orElseThrow(() -> new RuntimeException("No such user"));

        Card card = CardGenerator.createCard(user);

        cardRepository.save(card);

        return new CardOperationResponseData(
                card.getLastFourDigits(),
                "Card created");
    }

    @Transactional
    public CardOperationResponseData updateCardStatus(String cardNumber, CardStatus status) {
        Card card = cardRepository.findByLastFourDigits(CARD_TEMPLATE + cardNumber).orElseThrow();

        switch (status) {
            case ACTIVE -> card.setStatus(CardStatus.ACTIVE);
            case LOCKED -> card.setStatus(CardStatus.LOCKED);
        }

        cardRepository.save(card);

        return new CardOperationResponseData(
                CardFormatter.maskedCardNumber(cardNumber),
                "Card is " + status);
    }

    @Transactional
    public CardOperationResponseData deleteCard(String cardNumber) {
        Card card = cardRepository.findByLastFourDigits(CARD_TEMPLATE + cardNumber)
                .orElseThrow(() -> new NoSuchCardException(cardNumber));

        cardRepository.delete(card);

        return new CardOperationResponseData(
                CardFormatter.maskedCardNumber(cardNumber),
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
