package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddPostRequest {

  @NotEmpty
  private String title;

  @NotNull
  private String content;

  @NotEmpty
  private String postedBy;
  @NotEmpty
  private String level;
  @NotEmpty
  private String type;
  @NotEmpty
  private String fandomName;
  @NotNull
  private String postPhotoURL;

  public AddPostRequest(@NotEmpty String title,
      @NotNull String content, @NotEmpty String postedBy,
      @NotEmpty String level, @NotEmpty String type,
      @NotEmpty String fandomName,
      @NotNull String postPhotoURL) {
    this.title = title;
    this.content = content;
    this.postedBy = postedBy;
    this.level = level;
    this.type = type;
    this.fandomName = fandomName;
    this.postPhotoURL = postPhotoURL;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public String getLevel() {
    return level;
  }

  public String getType() {
    return type;
  }

  public String getFandomName() {
    return fandomName;
  }

  public String getPostPhotoURL() {
    return postPhotoURL;
  }
}
