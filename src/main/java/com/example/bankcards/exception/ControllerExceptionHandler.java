package com.example.bankcards.exception;

import com.example.bankcards.entity.response.ApiResponse;
import com.example.bankcards.entity.response.CardOperationResponseData;
import com.example.bankcards.exception.CardExceptions.NoSuchCardException;
import com.example.bankcards.exception.CardExceptions.NotOwnerException;
import com.example.bankcards.exception.UserExceptions.UserExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    //todo rewrite all exceptions to apiResponse
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistExc() {
        List<String> response = List.of("error", "User already exist");

        return new ResponseEntity<>(
                mapBuilder(response),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<Object> notOwnerExc() {
        List<String> response = List.of(
                "error", "User not owner one of cards");

        return new ResponseEntity<>(
                mapBuilder(response),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchCardException.class)
    public ResponseEntity<ApiResponse> noSuchCard(Exception ex, HttpServletRequest request) {
        String cardNumber = ex.getMessage();

        var data = new CardOperationResponseData(
                cardNumber, "Such card doesn't exist or have been deleted earlier");

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }

    private Map<String, String> mapBuilder(List<String> list) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < list.size() - 1; i = i + 2) {
            result.put(list.get(i), list.get(i+1));
        }

        return result;
    }
}
