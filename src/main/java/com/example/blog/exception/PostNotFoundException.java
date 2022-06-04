package com.example.blog.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post by id " + id + " does not exist");
    }
}
