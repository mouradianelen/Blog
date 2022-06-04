package com.example.blog.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User by username " + username + " does not exist");
    }
}
