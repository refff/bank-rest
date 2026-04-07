package com.example.bankcards.controller;

import com.example.bankcards.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            tags = "Админ"
    )
    public String helloAdmin() {
        return "Hello admin!";
    }

}
