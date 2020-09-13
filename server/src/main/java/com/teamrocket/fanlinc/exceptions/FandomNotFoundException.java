package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FandomNotFoundException extends RuntimeException {

    public FandomNotFoundException(String message) {
        super(message);
    }
}
