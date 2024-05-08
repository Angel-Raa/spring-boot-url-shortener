package com.github.angel.raa.exception;

public class MissingIdException extends RuntimeException{
    private String message;

    public MissingIdException(String message) {
        this.message = message;
    }
}
