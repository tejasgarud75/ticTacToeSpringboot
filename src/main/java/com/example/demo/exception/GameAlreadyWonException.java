package com.example.demo.exception;

public class GameAlreadyWonException extends RuntimeException {
    public GameAlreadyWonException(String message) {
        super(message);
    }
}
