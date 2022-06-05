package com.example.blog.exception;

public class NoSuchCategoryException extends RuntimeException{
    public NoSuchCategoryException(String title) {
        super("Category by title " + title + " does not exist");
    }
}
