package com.teamrocket.fanlinc.builders;

import com.teamrocket.fanlinc.responses.UserDetailsResponse;
import java.util.Date;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

public class UserDetailsResponseBuilder {

  private String username;
  private String firstName;
  private String lastName;
  @DateLong
  private Date dateOfBirth;
  private String location;
  private String bio;
  private String profilePhotoUrl;

  public UserDetailsResponseBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserDetailsResponseBuilder firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserDetailsResponseBuilder lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserDetailsResponseBuilder dateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public UserDetailsResponseBuilder location(String location) {
    this.location = location;
    return this;
  }

  public UserDetailsResponseBuilder bio(String bio) {
    this.bio = bio;
    return this;
  }

  public UserDetailsResponseBuilder profilePhotoUrl(String profilePhotoUrl) {
    this.profilePhotoUrl = profilePhotoUrl;
    return this;
  }

  public UserDetailsResponse build() {
    UserDetailsResponse response = new UserDetailsResponse();
    response.setUsername(this.username);
    response.setFirstName(this.firstName);
    response.setLastName(this.lastName);
    response.setDateOfBirth(this.dateOfBirth);
    response.setLocation(this.location);
    response.setBio(this.bio);
    response.setProfilePhotoUrl(this.profilePhotoUrl);
    return response;
  }
}
