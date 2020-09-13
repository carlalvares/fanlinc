package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;

public class AddJoinedFandomRequest {

  @NotEmpty
  private String level;

  @NotEmpty
  private String type;

  @NotEmpty
  private String fandomName;

  @NotEmpty
  private String username;

  public AddJoinedFandomRequest(@NotEmpty String level,
      @NotEmpty String type, @NotEmpty String fandomName,
      @NotEmpty String username) {
    this.level = level;
    this.type = type;
    this.fandomName = fandomName;
    this.username = username;
  }

  public String getFandomName() {
    return fandomName;
  }

  public String getLevel() {
    return level;
  }

  public String getType() {
    return type;
  }

  public String getUsername() {
    return username;
  }
}