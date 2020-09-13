package com.teamrocket.fanlinc.responses;

public class GetFandomDetailsResponse {

  private String fandomName;
  private String genre;
  private String description;
  private String displayPhotoURL;

  public GetFandomDetailsResponse(String fandomName, String genre, String description,
      String displayPhotoURL) {
    this.fandomName = fandomName;
    this.genre = genre;
    this.description = description;
    this.displayPhotoURL = displayPhotoURL;
  }

  public String getFandomName() {
    return fandomName;
  }

  public void setFandomName(String fandomName) {
    this.fandomName = fandomName;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisplayPhotoURL() {
    return displayPhotoURL;
  }

  public void setDisplayPhotoURL(String displayPhotoURL) {
    this.displayPhotoURL = displayPhotoURL;
  }
}
