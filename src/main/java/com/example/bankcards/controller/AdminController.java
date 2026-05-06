package com.example.bankcards.controller;

import com.example.bankcards.dto.CardNumberDTO;
import com.example.bankcards.dto.CardOwnerDTO;
import com.example.bankcards.dto.CardStatusRequest;
import com.example.bankcards.entity.response.ApiResponse;
import com.example.bankcards.entity.response.BlockAllCardsResponseData;
import com.example.bankcards.entity.response.CardOperationResponseData;
import com.example.bankcards.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/getAdmin")
    @Operation(
            description = "Получение статуса админа",
            tags = "Админ"
    )
    public ResponseEntity<?> becomeAdmin() {
        return adminService.grantAdminRoleToCurrentUser();
    }

    @GetMapping(value = "/helloAdmin")
    @Operation(
            description = "Привет для админа",
            tags = "Admin"
    )
    public String helloAdmin() {
        return "Hello admin!";
    }

    @GetMapping(value = "/allCards")
    @Operation(
            description = "Просмотр всех карт (номер, срок действия, статус, баланс)",
            tags = "Admin"
    )
    public ResponseEntity<?> getAllCards(){
        return new ResponseEntity<>(adminService.getAllCards(), HttpStatus.OK);
    }

    @PostMapping(value = "/createCard")
    @Operation(
            description = "Создание новой карты",
            tags = "Card"
    )
    public ResponseEntity<ApiResponse<CardOperationResponseData>> createNewCard(@RequestBody CardOwnerDTO cardOwnerDTO) {
        var data = adminService.createCard(cardOwnerDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(data));
    }

    @PutMapping(value = "/cardStatusRequest")
    @Operation(
            description = "Выполнение действий с картами(активация, блокировка)",
            tags = "Card"
    )
    public ResponseEntity<ApiResponse<CardOperationResponseData>> cardStatusUpdate(@RequestBody @Valid CardStatusRequest request) {
        var data = adminService.updateCardStatus(request.number(), request.action());

        return ResponseEntity.ok()
                .body(ApiResponse.ok(data));
    }

    @DeleteMapping(value = "/deleteCard")
    @Operation(
            description = "Удаление карт",
            tags = "Card"
    )
    public ResponseEntity<ApiResponse<CardOperationResponseData>> cardDelete(@RequestBody CardNumberDTO numberDTO) {
        var data = adminService.deleteCard(numberDTO.number());

        return ResponseEntity.ok()
                .body(ApiResponse.ok(data));
    }

    @GetMapping(value = "/allBlockRequests")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok()
                .body(adminService.getAllRequests());
    }

    @PutMapping(value = "/blockAllWaitedCards")
    public ResponseEntity<ApiResponse<BlockAllCardsResponseData>> blockAll() {
        var data = adminService.blockAll();

        return ResponseEntity.ok()
                .body(new ApiResponse<>(true, data));
    }


}
