package com.teamrocket.fanlinc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotInFandomException extends RuntimeException {
  public UserNotInFandomException(String message) {
    super(message);
  }
}
