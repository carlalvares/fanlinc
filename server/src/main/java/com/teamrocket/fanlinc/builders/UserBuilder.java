package com.teamrocket.fanlinc.builders;

import com.teamrocket.fanlinc.models.User;
import java.util.Date;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

public class UserBuilder {

  private String username;
  private String password;
  private String firstName;
  private String lastName;
  @DateLong
  private Date dateOfBirth;
  private String location;
  private String bio;
  private String profilePhotoUrl;

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserBuilder lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserBuilder dateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public UserBuilder location(String location) {
    this.location = location;
    return this;
  }

  public UserBuilder bio(String bio) {
    this.bio = bio;
    return this;
  }

  public UserBuilder profilePhotoUrl(String profilePhotoUrl) {
    this.profilePhotoUrl = profilePhotoUrl;
    return this;
  }

  public User build() {
    User user = new User();
    user.setUsername(this.username);
    user.setPassword(this.password);
    user.setFirstName(this.firstName);
    user.setLastName(this.lastName);
    user.setDateOfBirth(this.dateOfBirth);
    user.setLocation(this.location);
    user.setBio(this.bio);
    user.setProfilePhotoUrl(this.profilePhotoUrl);
    return user;
  }

}
