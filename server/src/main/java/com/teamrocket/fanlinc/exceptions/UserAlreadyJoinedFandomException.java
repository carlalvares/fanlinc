package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyJoinedFandomException extends RuntimeException {

    public UserAlreadyJoinedFandomException(String message){
        super(message);
    }

}