package com.example.bankcards.exception;

import com.example.bankcards.exception.CardExceptions.NotOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistExc() {
        List<String> response = List.of("error", "User already exist");

        return new ResponseEntity<>(
                mapBuilder(response),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<Object> notOwner() {
        List<String> response = List.of(
                "error", "User not owner one of cards");

        return new ResponseEntity<>(
                mapBuilder(response),
                HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> mapBuilder(List<String> list) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < list.size() - 1; i = i + 2) {
            result.put(list.get(i), list.get(i+1));
        }

        return result;
    }
}
