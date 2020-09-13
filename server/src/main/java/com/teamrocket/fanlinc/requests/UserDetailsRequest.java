package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;

public class UserDetailsRequest {

  public String getUsername() {
    return username;
  }

  @NotEmpty
  private String username;

}
