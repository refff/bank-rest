package com.example.bankcards.controller;

import com.example.bankcards.dto.CardNumberDTO;
import com.example.bankcards.dto.CardOperationDTO;
import com.example.bankcards.dto.TransferDTO;
import com.example.bankcards.entity.response.ApiResponse;
import com.example.bankcards.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Tag(
        name = "ClientController",
        description = "Пользовательский контроллер для просмотра своих карт, " +
                "переводов между ними, запроса блокировки карты и просмотра баланса ")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientController {

    private final ClientService clientService;

    @GetMapping(value = "/myCards")
    @Operation(
            description = "Просмотр всех карт пользователя (номер, срок действия, статус, баланс)",
            tags = "Client"
    )
    public ResponseEntity<?> getCardsRequest(@PageableDefault(size = 5) Pageable pageable,
                                             Authentication auth) {
        return clientService.getClientCards(pageable, auth);
    }

    @PutMapping(value = "/processTransfer")
    @Operation(
            description = "Перевод средств между картами пользователя",
            tags = "Client"
    )
    public ResponseEntity<?> transferMoneyRequest(@RequestBody @Valid TransferDTO transfer,
                                                  Authentication auth) {
        var data = clientService.processTransfer(transfer, auth);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, data));
    }

    @PutMapping(value = "/withdraw")
    public ResponseEntity<?> withdrawRequest(@RequestBody @Valid CardOperationDTO cardOperationDTO,
                                             Authentication auth) {
        var data = clientService.withdraw(cardOperationDTO, auth);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, data));
    }

    @PutMapping(value = "/deposit")
    public ResponseEntity<?> depositRequest(@RequestBody @Valid CardOperationDTO cardOperationDTO,
                                            Authentication auth) {
        var data = clientService.deposit(cardOperationDTO, auth);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, data));
    }

    @PutMapping(value = "/blockCardRequest")
    public ResponseEntity<?> blockCardRequest(@RequestBody CardNumberDTO cardNumberDTO,
                                              Authentication auth) {
        var data = clientService.blockCardRequest(cardNumberDTO.number(), auth);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, data));
    }
}
