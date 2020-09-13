package com.teamrocket.fanlinc.models;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity
public class Joined {

  @Id @GeneratedValue private Long id;

  private String level;

  private String type;

  @EndNode private Fandom fandom;

  @StartNode private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Fandom getFandom() {
    return fandom;
  }

  public void setFandom(Fandom fandom) {
    this.fandom = fandom;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
