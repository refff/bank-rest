package com.example.bankcards.exception;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User exist!");
    }
}