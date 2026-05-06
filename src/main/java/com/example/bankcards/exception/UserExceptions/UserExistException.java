package com.example.bankcards.exception.UserExceptions;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User exist!");
    }
}