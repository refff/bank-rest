package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Ответ c токеном доступа")
public class JwtAuthResponse {

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private String token;

    public String getToken() {
        return token;
    }

    public JwtAuthResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
