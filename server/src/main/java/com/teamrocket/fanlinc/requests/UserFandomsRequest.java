package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;

public class UserFandomsRequest {

  @NotEmpty private String username;

  public String getUsername() {
    return username;
  }
}
