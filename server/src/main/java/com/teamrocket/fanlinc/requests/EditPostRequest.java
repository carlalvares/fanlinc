package com.teamrocket.fanlinc.requests;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditPostRequest {

  private String title;

  private String content;

  private String level;

  private String type;

  private String fandom;

  private String postPhotoURL;
  @NotEmpty
  private String postedBy;
  @NotNull
  private Date postedTime;

  public EditPostRequest(@NotEmpty String postedBy,
      @NotNull Date postedTime) {
    this.postedBy = postedBy;
    this.postedTime = postedTime;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public String getLevel() {
    return level;
  }

  public String getType() {
    return type;
  }

  public String getPostPhotoURL() {
    return postPhotoURL;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public Date getPostedTime() {
    return postedTime;
  }

  public String getFandom() {
    return fandom;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPostPhotoURL(String postPhotoURL) {
    this.postPhotoURL = postPhotoURL;
  }

  public void setFandom(String fandom) {
    this.fandom = fandom;
  }

}
