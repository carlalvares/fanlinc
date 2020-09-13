package com.teamrocket.fanlinc.requests;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddUserRequest {

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  @NotNull
  private Date dateOfBirth;

  @NotNull
  private String location;

  @NotNull
  private String bio;

  @NotNull
  private String profilePhotoUrl;

  public AddUserRequest(@NotEmpty String username,
      @NotEmpty String password, @NotEmpty String firstName,
      @NotEmpty String lastName, @NotNull Date dateOfBirth,
      @NotNull String location, @NotNull String bio,
      @NotNull String profilePhotoUrl) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.location = location;
    this.bio = bio;
    this.profilePhotoUrl = profilePhotoUrl;
  }

  public String getPassword() {
    return password;
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

  public String getFirstName() {
    return firstName;
  }

  public String getUsername() {
    return username;
  }
}
