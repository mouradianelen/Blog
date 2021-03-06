package com.example.blog.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("User by email "+email+" already exists");
    }
}
