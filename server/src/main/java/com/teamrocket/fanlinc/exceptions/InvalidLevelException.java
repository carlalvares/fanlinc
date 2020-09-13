package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLevelException extends RuntimeException {

    public InvalidLevelException(String message){
        super(message);
    }

}