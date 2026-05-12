package com.example.bankcards.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private Instant timestamp;
    private T data;

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.timestamp = Instant.now();
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true,data);
    }

    public static <T> ApiResponse<T> fail(String message, T data) {
        return new ApiResponse<>(false,data);
    }
}
