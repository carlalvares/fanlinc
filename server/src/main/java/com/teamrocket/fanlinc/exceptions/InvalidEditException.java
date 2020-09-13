package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEditException extends RuntimeException {

  public InvalidEditException(String message) {
    super(message);
  }
}
