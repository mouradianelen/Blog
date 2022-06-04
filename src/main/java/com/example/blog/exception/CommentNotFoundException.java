package com.example.blog.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id) {
        super("Comment by id " + id + " does not exist");
    }
}
