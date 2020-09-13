package com.teamrocket.fanlinc.models;

public class UserFandomDetails {

  private String fandomName;
  private String level;
  private String type;

  public String getFandomName() {
    return fandomName;
  }

  public void setFandomName(String fandomName) {
    this.fandomName = fandomName;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UserFandomDetails(String fandomName, String level, String type) {
    this.fandomName = fandomName;
    this.level = level;
    this.type = type;
  }
}
