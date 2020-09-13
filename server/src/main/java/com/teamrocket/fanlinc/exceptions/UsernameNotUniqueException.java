package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameNotUniqueException extends RuntimeException{

    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
