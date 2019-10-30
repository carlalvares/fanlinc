package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddFandomRequest {

  @NotEmpty
  private String fandomName;

  @NotEmpty
  private String genre;

  @NotEmpty
  private String description;

  @NotNull
  private String displayPhotoURL;

  public String getFandomName() {
    return fandomName;
  }

  public String getGenre() {
    return genre;
  }

  public String getDescription() {
    return description;
  }

  public String getDisplayPhotoURL() {
    return displayPhotoURL;
  }
}
