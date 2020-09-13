package com.teamrocket.fanlinc.builders;

import com.teamrocket.fanlinc.models.Fandom;

public class FandomBuilder {

  private String fandomName;
  private String genre;
  private String description;
  private String displayPhotoURL;

  public FandomBuilder fandomName(String fandomName) {
    this.fandomName = fandomName;
    return this;
  }

  public FandomBuilder genre(String genre) {
    this.genre = genre;
    return this;
  }

  public FandomBuilder description(String description) {
    this.description = description;
    return this;
  }

  public FandomBuilder displayPhotoURL(String displayPhotoURL) {
    this.displayPhotoURL = displayPhotoURL;
    return this;
  }

  public Fandom build() {
    Fandom newFandom = new Fandom();
    newFandom.setFandomName(this.fandomName);
    newFandom.setDescription(this.description);
    newFandom.setGenre(this.genre);
    newFandom.setDisplayPhotoURL(this.displayPhotoURL);
    return newFandom;
  }
}
