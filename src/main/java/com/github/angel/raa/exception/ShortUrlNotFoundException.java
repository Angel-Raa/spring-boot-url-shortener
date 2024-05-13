package com.github.angel.raa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends  RuntimeException{
    private String msg;

    public ShortUrlNotFoundException(String msg) {
        this.msg = msg;
    }
}
