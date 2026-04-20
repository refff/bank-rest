package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.service.AuthService;
import com.example.bankcards.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "AuthController",
        description = "Контроллер для регистрации пользователей и получения ими  JWT токена")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/hello")
    @Operation(summary = "Доступен всем пользователям")
    public String sayHello() {
        return "hello";
    }

    @PostMapping(value = "/signup")
    @Operation(
            description = "Регистрация пользователей",
            tags = "Аутентификация",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully created"),
                    @ApiResponse(responseCode = "409", description = "This username is exist")}
    )
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userData) {
        return authService.signUp(userData);
    }

    @GetMapping("/token")
    public ResponseEntity<?> getAuthToken() {
        return authService.getToken();
    }


    @GetMapping(value = "/hellos")
    @Operation(summary = "Доступен только авторизованным пользователям (тестовый эндпоинт)")
    public String secretHello() {
        return "hello";
    }
}
