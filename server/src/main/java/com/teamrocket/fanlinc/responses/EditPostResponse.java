package com.teamrocket.fanlinc.responses;

import com.teamrocket.fanlinc.models.Post;
import java.util.Date;
import java.util.List;;

public class EditPostResponse {

  private String username;
  private Date postedTime;
  private List<String> modifiedFields;


  public EditPostResponse(String username, Date postedTime, List<String> modifiedFields) {
    this.username = username;
    this.postedTime = postedTime;
    this.modifiedFields = modifiedFields;
  }

  public String getUsername() {
    return username;
  }

  public Date getPostedTime() {
    return postedTime;
  }

  public List<String> getModifiedFields() {
    return modifiedFields;
  }
}
