package com.teamrocket.fanlinc.models;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Fandom {
  @Id @GeneratedValue private Long id;
private String  fandomName;
  private String genre;
  private String description;
  private String displayPhotoURL;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
