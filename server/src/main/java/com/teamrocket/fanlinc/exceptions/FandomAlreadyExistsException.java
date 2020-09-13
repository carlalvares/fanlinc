package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FandomAlreadyExistsException extends RuntimeException {

  public FandomAlreadyExistsException(String message){
    super(message);
  }

}
