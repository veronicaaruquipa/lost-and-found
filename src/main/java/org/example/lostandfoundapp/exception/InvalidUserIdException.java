package org.example.lostandfoundapp.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}