package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private AuthService authService;

    @Autowired
    public Controller(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/api/hello")
    public String sayHello() {
        return "hello";
    }

    @PostMapping(value = "/api/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userData) {
        return authService.signUp(userData);
    }

    @GetMapping(value = "/api/hellos")
    public String secretHello() {
        return "hello";
    }
}
