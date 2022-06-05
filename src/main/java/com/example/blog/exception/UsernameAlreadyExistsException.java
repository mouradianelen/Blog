package com.example.blog.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("User by username "+username+" already exists");
    }
}
