package com.teamrocket.fanlinc.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teamrocket.fanlinc.builders.UserBuilder;
import com.teamrocket.fanlinc.exceptions.UserNotFoundException;
import com.teamrocket.fanlinc.exceptions.UsernameNotUniqueException;
import com.teamrocket.fanlinc.models.Fandom;
import com.teamrocket.fanlinc.models.Joined;
import com.teamrocket.fanlinc.models.User;
import com.teamrocket.fanlinc.models.UserFandomDetails;
import com.teamrocket.fanlinc.repositories.JoinedRepository;
import com.teamrocket.fanlinc.repositories.UserRepository;
import com.teamrocket.fanlinc.requests.AddUserRequest;
import com.teamrocket.fanlinc.responses.AddUserResponse;
import com.teamrocket.fanlinc.responses.UserDetailsResponse;
import com.teamrocket.fanlinc.responses.UserFandomsResponse;
import com.teamrocket.fanlinc.responses.ValidateUserResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountServiceImplTests {

  private static final String EXAMPLE_USERNAME = "example";
  private static final String EXAMPLE_PASSWORD = "password";
  private static final String EXAMPLE_BIO = "bio";
  private static final String EXAMPLE_FIRST_NAME = "first";
  private static final String EXAMPLE_LAST_NAME = "last";
  private static final String EXAMPLE_LOCATION = "location";
  private static final String EXAMPLE_PROFILE_PHOTO_URL = "http://www.example.com";
  private static final Date EXAMPLE_DOB = new Date(632282416);
  private static final String DIFFERENT_USERNAME = "different";
  private static final String WRONG_PASSWORD = "wrong";
  private static final String EXAMPLE_FANDOM = "example";
  private static final String EXAMPLE_FANDOM_2 = "example2";
  private static final String EXAMPLE_LEVEL = "1";
  private static final String EXAMPLE_LEVEL_2 = "2";
  private static final String EXAMPLE_TYPE = "General";
  private static final String EXAMPLE_TYPE_2= "Cosplayer";

  @InjectMocks
  AccountServiceImpl accountService;

  @Mock
  UserRepository userRepository;

  @Mock
  JoinedRepository joinedRepository;

  private User user;
  private List<Joined> fandomDetails;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    user = new UserBuilder()
        .username(EXAMPLE_USERNAME)
        .password(EXAMPLE_PASSWORD)
        .bio(EXAMPLE_BIO)
        .firstName(EXAMPLE_FIRST_NAME)
        .lastName(EXAMPLE_LAST_NAME)
        .dateOfBirth(EXAMPLE_DOB)
        .location(EXAMPLE_LOCATION)
        .profilePhotoUrl(EXAMPLE_PROFILE_PHOTO_URL)
        .build();
    when(userRepository.findByUsername(EXAMPLE_USERNAME)).thenReturn(user);
  }

  @Test
  public void validateUser_UserDoesNotExist_ExceptionThrown() {
    when(userRepository.findByUsername(EXAMPLE_USERNAME)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> accountService.validateUser(EXAMPLE_USERNAME, EXAMPLE_PASSWORD)
    ).withMessage("User with username " + EXAMPLE_USERNAME + " not found");

  }

  @Test
  public void validateUser_IncorrectPassword_ResponseWithAcceptedFalse() {
    ValidateUserResponse response = accountService.validateUser(EXAMPLE_USERNAME, WRONG_PASSWORD);

    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USERNAME);
    assertThat(response.isAccepted()).isFalse();
  }

  @Test
  public void validateUser_CorrectPassword_ResponseWithAcceptedTrue() {
    ValidateUserResponse response = accountService
        .validateUser(EXAMPLE_USERNAME, EXAMPLE_PASSWORD);

    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USERNAME);
    assertThat(response.isAccepted()).isTrue();
  }

  @Test
  public void addUser_UserAlreadyExists_ExceptionThrown() {
    AddUserRequest request = new AddUserRequest(
        EXAMPLE_USERNAME,
        EXAMPLE_PASSWORD,
        EXAMPLE_FIRST_NAME,
        EXAMPLE_LAST_NAME,
        EXAMPLE_DOB,
        EXAMPLE_LOCATION,
        EXAMPLE_BIO,
        EXAMPLE_PROFILE_PHOTO_URL
    );

    assertThatExceptionOfType(UsernameNotUniqueException.class).isThrownBy(
        () -> accountService.addUser(request)
    ).withMessage("User with username " + EXAMPLE_USERNAME + " already exists");

  }

  @Test
  public void addUser_NewUser_CorrectResponse() {
    AddUserRequest request = new AddUserRequest(
        DIFFERENT_USERNAME,
        EXAMPLE_PASSWORD,
        EXAMPLE_FIRST_NAME,
        EXAMPLE_LAST_NAME,
        EXAMPLE_DOB,
        EXAMPLE_LOCATION,
        EXAMPLE_BIO,
        EXAMPLE_PROFILE_PHOTO_URL
    );

    when(userRepository.findByUsername(DIFFERENT_USERNAME)).thenReturn(null);

    AddUserResponse response = accountService.addUser(request);

    verify(userRepository).save(any(User.class));
    assertThat(response.getUsername()).isEqualTo(DIFFERENT_USERNAME);
  }

  @Test
  public void getUserDetails_UserDoesNotExist_ExceptionThrown() {
    when(userRepository.findByUsername(EXAMPLE_USERNAME)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> accountService.getUserDetails(EXAMPLE_USERNAME)
    ).withMessage("User with username " + EXAMPLE_USERNAME + " not found");
  }

  @Test
  public void getUserDetails_UserExists_CorrectResponse() {
    UserDetailsResponse response = accountService.getUserDetails(EXAMPLE_USERNAME);

    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USERNAME);
    assertThat(response.getBio()).isEqualTo(EXAMPLE_BIO);
    assertThat(response.getDateOfBirth()).isEqualTo(EXAMPLE_DOB);
    assertThat(response.getFirstName()).isEqualTo(EXAMPLE_FIRST_NAME);
    assertThat(response.getLastName()).isEqualTo(EXAMPLE_LAST_NAME);
    assertThat(response.getLocation()).isEqualTo(EXAMPLE_LOCATION);
    assertThat(response.getProfilePhotoUrl()).isEqualTo(EXAMPLE_PROFILE_PHOTO_URL);
  }

  @Test
  public void getUserFandoms_UserDoesNotExist_ExceptionThrown() {
    when(userRepository.findByUsername(EXAMPLE_USERNAME)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> accountService.getUserFandoms(EXAMPLE_USERNAME)
    ).withMessage("User with username " + EXAMPLE_USERNAME + " not found");
  }

  @Test
  public void getUserFandoms_UserExists_CorrectResponse() {
    fandomDetails = new ArrayList<>();
    Joined exampleRelation = new Joined();
    Fandom exampleFandomModel = new Fandom();
    exampleFandomModel.setFandomName(EXAMPLE_FANDOM);
    exampleRelation.setFandom(exampleFandomModel);
    exampleRelation.setLevel(EXAMPLE_LEVEL);
    exampleRelation.setType(EXAMPLE_TYPE);
    fandomDetails.add(exampleRelation);

    Joined exampleRelation2 = new Joined();
    Fandom exampleFandomModel2 = new Fandom();
    exampleFandomModel2.setFandomName(EXAMPLE_FANDOM_2);
    exampleRelation2.setFandom(exampleFandomModel2);
    exampleRelation2.setLevel(EXAMPLE_LEVEL_2);
    exampleRelation2.setType(EXAMPLE_TYPE_2);
    fandomDetails.add(exampleRelation2);
    
    when(joinedRepository.findJoinedByUsername(EXAMPLE_USERNAME)).thenReturn(fandomDetails);
    UserFandomsResponse response = accountService.getUserFandoms(EXAMPLE_USERNAME);

    List<UserFandomDetails> fandomDetailsResponse = new ArrayList<>();
    fandomDetailsResponse.add(new UserFandomDetails(EXAMPLE_FANDOM, EXAMPLE_LEVEL, EXAMPLE_TYPE));
    fandomDetailsResponse
        .add(new UserFandomDetails(EXAMPLE_FANDOM_2, EXAMPLE_LEVEL_2, EXAMPLE_TYPE_2));
    List<UserFandomDetails> actualResponse = response.getUserFandoms();
    assertThat(actualResponse.size()).isEqualTo(2);
    for (int i = 0; i < 2; i++) {
      assertThat(actualResponse.get(i).getFandomName())
          .isEqualTo(fandomDetailsResponse.get(i).getFandomName());
      assertThat(actualResponse.get(i).getLevel())
          .isEqualTo(fandomDetailsResponse.get(i).getLevel());
      assertThat(actualResponse.get(i).getType()).isEqualTo(fandomDetailsResponse.get(i).getType());
    }
  }

}
