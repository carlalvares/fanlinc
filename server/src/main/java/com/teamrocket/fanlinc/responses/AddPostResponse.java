package com.teamrocket.fanlinc.responses;

public class AddPostResponse {
  private String title;
  private String postedBy;
  private String fandomName;

  public AddPostResponse(String title, String postedBy, String fandomName) {
    this.title = title;
    this.postedBy = postedBy;
    this.fandomName = fandomName;
  }

  public String getTitle() {
    return title;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public String getFandomName() {
    return fandomName;
  }
}
