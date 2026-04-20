package com.example.bankcards.controller;

import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getCards() {
        return clientService.getClientCards();
    }
}
