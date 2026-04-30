package com.example.bankcards.controller;

import com.example.bankcards.dto.CardOperationDTO;
import com.example.bankcards.dto.TransferDTO;
import com.example.bankcards.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Tag(
        name = "ClientController",
        description = "Пользовательский контроллер для просмотра своих карт, " +
                "переводов между ними, запроса блокировки карты и просмотра баланса ")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/myCards")
    @Operation(
            description = "Просмотр всех карт пользователя (номер, срок действия, статус, баланс)",
            tags = "Client"
    )
    public ResponseEntity<?> getCards(@PageableDefault(size = 5) Pageable pageable) {
        return clientService.getClientCards(pageable);
    }

    @PostMapping(value = "/processTransfer")
    @Operation(
            description = "Перевод средств между картами пользователя",
            tags = "Client"
    )
    public ResponseEntity<?> transferMoney(@RequestBody @Valid TransferDTO transfer) {
        return clientService.processTransfer(transfer);
    }

    @PostMapping(value = "/atmOperation")
    public ResponseEntity<?> atmOperation(@RequestBody @Valid CardOperationDTO cardOperationDTO) {
        return clientService.processTransaction(cardOperationDTO);
    }

    @PostMapping(value = "/blockCardRequest")
    public ResponseEntity<?> blockCardRequest(@RequestBody CardNumberDTO cardNumberDTO) {
        return clientService.blockRequest(cardNumberDTO);
    }

    public record CardNumberDTO(String number) {}
}
