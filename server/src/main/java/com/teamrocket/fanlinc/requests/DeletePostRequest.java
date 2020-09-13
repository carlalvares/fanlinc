package com.teamrocket.fanlinc.requests;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DeletePostRequest {

  @NotEmpty private String postedBy;

  @NotNull private Date postedTime;

  public DeletePostRequest(@NotEmpty String postedBy,
      @NotNull Date postedTime) {
    this.postedBy = postedBy;
    this.postedTime = postedTime;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public Date getPostedTime() {
    return postedTime;
  }
}
