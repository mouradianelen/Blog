package com.example.blog.exception;

public class RatingOutOfBoundsException extends RuntimeException {
    public RatingOutOfBoundsException() {
        super("Please choose a number between 1 and 5");
    }
}
