package com.teamrocket.fanlinc.models;

import java.util.Date;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

@NodeEntity
public class User {

  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  @DateLong
  private Date dateOfBirth;
  private String location;
  private String bio;
  private String profilePhotoUrl;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public String getLocation() {
    return location;
  }

  public String getBio() {
    return bio;
  }

  public String getProfilePhotoUrl() {
    return profilePhotoUrl;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public void setProfilePhotoUrl(String profilePhotoUrl) {
    this.profilePhotoUrl = profilePhotoUrl;
  }
}
