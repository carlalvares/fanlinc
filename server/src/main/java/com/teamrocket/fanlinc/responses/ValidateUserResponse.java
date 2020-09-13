package com.teamrocket.fanlinc.responses;

public class ValidateUserResponse {

  private String username;

  private boolean accepted;

  public ValidateUserResponse(String username, boolean accepted) {
    this.username = username;
    this.accepted = accepted;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAccepted() {
    return accepted;
  }
}
