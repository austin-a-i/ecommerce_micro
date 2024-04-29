package com.microservices.userservice.exceptions;

public class IncorrectDetailsException extends RuntimeException {

    public IncorrectDetailsException(String message) {
        super(message);
    }
}
