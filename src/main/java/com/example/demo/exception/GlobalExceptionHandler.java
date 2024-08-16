package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for managing application-wide exceptions.
 *
 * This class handles various types of exceptions that might occur throughout
 * the application. It provides meaningful HTTP responses for different exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@code UserNotFoundException} and returns a 404 Not Found status.
     *
     * @param ex The exception to handle.
     * @return A {@code ResponseEntity} containing the exception message and a 404 status.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@code GameNotFoundException} and returns a 404 Not Found status.
     *
     * @param ex The exception to handle.
     * @return A {@code ResponseEntity} containing the exception message and a 404 status.
     */
    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<String> handleGameNotFoundException(GameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@code IllegalStateException} and returns a 400 Bad Request status.
     *
     * @param ex The exception to handle.
     * @return A {@code ResponseEntity} containing the exception message and a 400 status.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@code UserEmailAlreadyExistsException} and returns a 409 Conflict status.
     *
     * @param ex The exception to handle.
     * @return A {@code ResponseEntity} containing the exception message and a 409 status.
     */
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<String> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@code GameAlreadyWonException} and returns a 409 Conflict status.
     *
     * @param ex The exception to handle.
     * @return A {@code ResponseEntity} containing the exception message and a 409 status.
     */
    @ExceptionHandler(GameAlreadyWonException.class)
    public ResponseEntity<String> handleGameAlreadyWonException(GameAlreadyWonException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
