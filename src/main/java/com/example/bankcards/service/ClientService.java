package com.example.bankcards.service;

import com.example.bankcards.dto.CardOperationDTO;
import com.example.bankcards.dto.TransferDTO;
import com.example.bankcards.entity.BlockRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.BlockRequestStatus;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.response.ATMResponseData;
import com.example.bankcards.entity.response.CardOperationResponseData;
import com.example.bankcards.entity.response.TransferResponseData;
import com.example.bankcards.exception.CardExceptions.*;
import com.example.bankcards.exception.UserExceptions.UserExistException;
import com.example.bankcards.repository.BlockRequestRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;


//todo remove this
import static com.example.bankcards.service.AdminService.CARD_TEMPLATE;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final BlockRequestRepository requestRepository;


    @Transactional
    public ResponseEntity<?> getClientCards(Pageable pageable, Authentication auth) {
        String username = auth.getName();

        Page<Card> cardList = cardRepository.findAllByUsername(username, pageable);

        return new ResponseEntity<>(cardList.getContent(), HttpStatus.OK);
    }

    @Transactional
    public TransferResponseData processTransfer(TransferDTO transferDTO, Authentication auth) {

        User user = getUser(auth);
        Transfer transfer = convertFromDTO(transferDTO);

        validateCardOwner(transfer.getFromCard(), user);
        validateCardOwner(transfer.getToCard(), user);
        validateEnoughMoney(transfer.getFromCard(), transfer.getValue());
        validateCardStatus(transfer.getFromCard());
        validateCardStatus(transfer.getToCard());

        return transferMoney(transfer);
    }

    @Transactional
    public ATMResponseData withdraw(CardOperationDTO cardOperationDTO, Authentication auth) {

        Card card = getCard(cardOperationDTO.number());
        User user = getUser(auth);

        validateCardOwner(card, user);
        validateCardStatus(card);
        validateEnoughMoney(card, cardOperationDTO.amount());

        card.setBalance(card.getBalance().subtract(cardOperationDTO.amount()));
        cardRepository.save(card);

        return new ATMResponseData(
                card.getCardNumber(),
                "WITHDRAW is completed!",
                cardOperationDTO.amount()
        );
    }

    @Transactional
    public ATMResponseData deposit(CardOperationDTO cardOperationDTO, Authentication auth) {

        Card card = getCard(cardOperationDTO.number());
        User user = getUser(auth);

        validateCardOwner(card, user);
        validateCardStatus(card);

        card.setBalance(card.getBalance().add(cardOperationDTO.amount()));
        cardRepository.save(card);

        return new ATMResponseData(
                card.getCardNumber(),
                "DEPOSIT is completed!",
                cardOperationDTO.amount()
        );
    }

    @Transactional
    public CardOperationResponseData blockCardRequest(String cardNumber, Authentication auth) {

        Card card = getCard(cardNumber);
        User user = getUser(auth);

        validateCardOwner(card, user);

        BlockRequest request = BlockRequest.builder()
                .card(card)
                .status(BlockRequestStatus.WAITING)
                .build();

        requestRepository.save(request);

        return new CardOperationResponseData(
                cardNumber,
                "A request to block the card has been created");

        /*return new ResponseEntity<>(Map.of(
                "status", "A request to block the card has been created",
                "card number", cardNumber
        ),
                HttpStatus.OK);*/
    }

    private Transfer convertFromDTO(TransferDTO transfer) {
        return new Transfer(
                getCard(transfer.fromCard()),
                getCard(transfer.toCard()),
                transfer.amount()
        );
    }

    private User getUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private Card getCard(String number) {
        return cardRepository.findByCardNumber(CARD_TEMPLATE + number)
                .orElseThrow(() -> new NoSuchCardException("Card not found"));
    }

    private void validateCardOwner(Card card, User user) {
        if (!card.getOwner().equals(user)) {
            throw new NotOwnerException();
        }
    }

    private void validateEnoughMoney(Card card, BigDecimal amount) {
        if (card.getBalance().compareTo(amount) < 0)
            throw new NotEnoughMoneyException();
    }

    private void validateCardStatus(Card card) {
        if (card.getStatus() == CardStatus.LOCKED)
            throw new CardIsLockedException(card.getCardNumber());
        else if (card.getStatus() == CardStatus.EXPIRED)
            throw new CardIsExpiredException(card.getCardNumber());
    }

    private TransferResponseData transferMoney(Transfer transfer) {
        Card from = transfer.getFromCard();
        Card to = transfer.getToCard();

        from.setBalance(from.getBalance().subtract(transfer.getValue()));
        to.setBalance(to.getBalance().add(transfer.getValue()));

        cardRepository.save(from);
        cardRepository.save(to);

        return new TransferResponseData(
                "Transfer completed!",
                transfer.getValue());

        /*return new ResponseEntity<>(Map.of(
                "status", "Transfer completed!"
        ), HttpStatus.OK);*/
    }

}
