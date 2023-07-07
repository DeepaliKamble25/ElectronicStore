package com.electronic.store.exception;

public class UserNotFoundException extends RuntimeException{

    String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
