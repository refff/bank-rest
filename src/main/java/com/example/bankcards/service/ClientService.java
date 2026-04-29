package com.example.bankcards.service;

import com.example.bankcards.controller.ClientController;
import com.example.bankcards.dto.CardOperationDTO;
import com.example.bankcards.dto.TransferDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardExceptions.NotEnoughMoneyException;
import com.example.bankcards.exception.CardExceptions.NotOwnerException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static com.example.bankcards.service.AdminService.cardTemplate;

@Service
public class ClientService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public ClientService(CardRepository cardRepository,
                         UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> getClientCards(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Page<Card> cardList = cardRepository.findAllByUsername(username, pageable);

        return new ResponseEntity<>(cardList.getContent(), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> processTransfer(TransferDTO transferDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = getUser(auth);
        Transfer transfer = convertFromDTO(transferDTO);

        validateCardOwner(transfer.getFromCard(), user);
        validateCardOwner(transfer.getToCard(), user);
        validateEnoughMoney(transfer.getFromCard(), transfer.getValue());

        return transferMoney(transfer);
    }

    @Transactional
    public ResponseEntity<?> processTransaction(CardOperationDTO cardOperationDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Card card = getCard(cardOperationDTO.number());
        User user = getUser(auth);

        validateCardOwner(card, user);

        return applyTransaction(card, cardOperationDTO);
    }

    @Transactional
    public ResponseEntity<?> blockRequest(ClientController.CardNumberDTO numberDTO) {


        return null;
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
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Card getCard(String number) {
        return cardRepository.findByCardNumber(cardTemplate + number)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
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

    private ResponseEntity<?> transferMoney(Transfer transfer) {
        Card from = transfer.getFromCard();
        Card to = transfer.getToCard();

        from.setBalance(from.getBalance().subtract(transfer.getValue()));
        to.setBalance(to.getBalance().add(transfer.getValue()));

        cardRepository.save(from);
        cardRepository.save(to);

        return new ResponseEntity<>(Map.of(
                "status", "Transfer completed!"
        ), HttpStatus.OK);
    }

    private ResponseEntity<?> applyTransaction(Card card, CardOperationDTO dto) {

        switch (dto.operation()) {
            case "WITHDRAW" -> withdraw(card, dto.amount());
            case "DEPOSIT" -> deposit(card, dto.amount());
            default ->
                    throw new IllegalArgumentException("No such operation, or it is null");
        }

        cardRepository.save(card);

        return new ResponseEntity<>(Map.of(
                "operation", dto.operation(),
                "status", "completed"),
                HttpStatus.OK);
    }

    private void withdraw(Card card, BigDecimal amount) {
        validateEnoughMoney(card, amount);

        card.setBalance(card.getBalance().subtract(amount));
    }

    private void deposit(Card card, BigDecimal amount) {
        card.setBalance(card.getBalance().add(amount));
    }
}
