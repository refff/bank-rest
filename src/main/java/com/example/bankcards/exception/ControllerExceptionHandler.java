package com.example.bankcards.exception;

import com.example.bankcards.entity.response.ApiResponse;
import com.example.bankcards.entity.response.ErrorResponseData;
import com.example.bankcards.exception.CardExceptions.CardIsExpiredException;
import com.example.bankcards.exception.CardExceptions.CardIsLockedException;
import com.example.bankcards.exception.CardExceptions.NoSuchCardException;
import com.example.bankcards.exception.CardExceptions.NotOwnerException;
import com.example.bankcards.exception.UserExceptions.UserExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistExc() {

        var data = new ErrorResponseData(
                "User already exist");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, data));
    }

    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<ApiResponse> notOwnerExc() {

        var data = new ErrorResponseData("User not owner one of cards");

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }

    @ExceptionHandler(NoSuchCardException.class)
    public ResponseEntity<ApiResponse> noSuchCard() {

        var data = new ErrorResponseData(
                "Such card doesn't exist or have been deleted earlier");

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }

    @ExceptionHandler(CardIsLockedException.class)
    public ResponseEntity<ApiResponse> cardIsLocked(Exception ex) {
        String cardNumber = ex.getMessage();

        var data = new ErrorResponseData(
                "Card " + cardNumber + " is locked!");

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }

    @ExceptionHandler(CardIsExpiredException.class)
    public ResponseEntity<ApiResponse> cardIsExpired(Exception ex) {
        String cardNumber = ex.getMessage();

        var data = new ErrorResponseData(
                "Card has expired!");

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors().get(0).getDefaultMessage();

        var data = new ErrorResponseData(message);

        return ResponseEntity.badRequest()
                .body(new ApiResponse(false, data));
    }
}
