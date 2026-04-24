package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.CardStatusRequest;
import com.example.bankcards.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/getAdmin")
    @Operation(
            description = "Получение статуса админа",
            tags = "Админ"
    )
    public ResponseEntity<?> becomeAdmin() {
        return adminService.getAdminAccess();
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
    public ResponseEntity<?> createNewCard(@RequestBody CardDTO cardDTO) {
        return adminService.createCard(cardDTO);
    }

    @PostMapping(value = "/cardStatusRequest")
    @Operation(
            description = "Выполнение действий с картами(активация, блокировка)",
            tags = "Card"
    )
    public ResponseEntity<?> cardAction(@RequestBody @Valid CardStatusRequest request) {
        return adminService.processCardAction(request.number(), request.action());
    }

    @PostMapping(value = "/deleteCard")
    @Operation(
            description = "Удаление карт",
            tags = "Card"
    )
    public ResponseEntity<?> cardDelete(@RequestBody @Valid CardStatusRequest request) {
        return adminService.deleteCard(request.number());
    }

}
