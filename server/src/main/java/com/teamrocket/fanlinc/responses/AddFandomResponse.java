package com.teamrocket.fanlinc.responses;

public class AddFandomResponse {
  private String fandomName;

  public AddFandomResponse(String fandomName) {
    this.fandomName = fandomName;
  }

  public String getFandomName() {
    return fandomName;
  }
}
