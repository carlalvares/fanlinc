package com.teamrocket.fanlinc.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teamrocket.fanlinc.builders.FandomBuilder;
import com.teamrocket.fanlinc.builders.JoinedBuilder;
import com.teamrocket.fanlinc.exceptions.*;
import com.teamrocket.fanlinc.models.Fandom;
import com.teamrocket.fanlinc.models.Joined;
import com.teamrocket.fanlinc.models.User;
import com.teamrocket.fanlinc.repositories.FandomRepository;
import com.teamrocket.fanlinc.repositories.JoinedRepository;
import com.teamrocket.fanlinc.repositories.UserRepository;
import com.teamrocket.fanlinc.requests.AddFandomRequest;
import com.teamrocket.fanlinc.requests.AddJoinedFandomRequest;
import com.teamrocket.fanlinc.requests.LeaveFandomRequest;
import com.teamrocket.fanlinc.responses.AddFandomResponse;
import com.teamrocket.fanlinc.responses.AddJoinedFandomResponse;
import com.teamrocket.fanlinc.responses.GetFandomDetailsResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FandomServiceImplTests {

  private static final String EXAMPLE_FANDOM_NAME = "fandom";
  private static final String EXAMPLE_DESCRIPTION = "description";
  private static final String EXAMPLE_GENRE = "genre";
  private static final String EXAMPLE_DISPLAY_PHOTO_URL = "http://www.example.com";
  private static final String DIFFERENT_FANDOM_NAME = "other";
  private static final String EXAMPLE_LEVEL = "1";
  private static final String EXAMPLE_TYPE = "General";
  private static final String EXAMPLE_USER = "user";
  private static final List<String> levels = Arrays.asList("1", "2", "3", "4");
  private static final List<String> types = Arrays.asList("General", "Cosplayer", "Vendor/Artist");

  @InjectMocks
  FandomServiceImpl fandomService;

  @Mock
  JoinedRepository joinedRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  FandomRepository fandomRepository;

  private Fandom fandom;
  private User user;
  private Joined joined;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    fandom = new FandomBuilder()
        .fandomName(EXAMPLE_FANDOM_NAME)
        .description(EXAMPLE_DESCRIPTION)
        .genre(EXAMPLE_GENRE)
        .displayPhotoURL(EXAMPLE_DISPLAY_PHOTO_URL)
        .build();
    when(fandomRepository.findByFandomName(EXAMPLE_FANDOM_NAME)).thenReturn(fandom);
    user = mock(User.class);
    when(userRepository.findByUsername(EXAMPLE_USER)).thenReturn(user);
    joined = new JoinedBuilder()
        .user(user)
        .fandom(fandom)
        .level(EXAMPLE_LEVEL)
        .type(EXAMPLE_TYPE)
        .build();
    when(joinedRepository.findJoinedByUsernameAndFandomName(EXAMPLE_USER, EXAMPLE_FANDOM_NAME))
        .thenReturn(null);
  }

  @Test
  public void addFandom_FandomAlreadyExists_ThrowsException() {
    AddFandomRequest request = new AddFandomRequest(
        EXAMPLE_FANDOM_NAME, EXAMPLE_GENRE, EXAMPLE_DESCRIPTION, EXAMPLE_DISPLAY_PHOTO_URL
    );

    assertThatExceptionOfType(FandomAlreadyExistsException.class).isThrownBy(
        () -> fandomService.addFandom(request)
    ).withMessage("A fandom with the name " + EXAMPLE_FANDOM_NAME + " already exists");
  }

  @Test
  public void addFandom_NewFandom_CorrectResponse() {
    AddFandomRequest request = new AddFandomRequest(
        DIFFERENT_FANDOM_NAME, EXAMPLE_GENRE, EXAMPLE_DESCRIPTION, EXAMPLE_DISPLAY_PHOTO_URL
    );

    when(fandomRepository.findByFandomName(DIFFERENT_FANDOM_NAME)).thenReturn(null);

    AddFandomResponse response = fandomService.addFandom(request);

    verify(fandomRepository).save(any(Fandom.class));
    assertThat(response.getFandomName()).isEqualTo(DIFFERENT_FANDOM_NAME);
  }

  @Test
  public void addJoinedFandom_InvalidType_ThrowsException() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        EXAMPLE_LEVEL, "wrong", EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    assertThatExceptionOfType(InvalidTypeException.class).isThrownBy(
        () -> fandomService.addJoinedFandom(request)
    ).withMessage("Type must be one of " + types.toString());
  }

  @Test
  public void addJoinedFandom_InvalidLevel_ThrowsException() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        "wrong", EXAMPLE_TYPE, EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    assertThatExceptionOfType(InvalidLevelException.class).isThrownBy(
        () -> fandomService.addJoinedFandom(request)
    ).withMessage("Level must be one of " + levels.toString());
  }

  @Test
  public void addJoinedFandom_UserDoesNotExist_ThrowsException() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        EXAMPLE_LEVEL, EXAMPLE_TYPE, EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    when(userRepository.findByUsername(EXAMPLE_USER)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> fandomService.addJoinedFandom(request)
    ).withMessage("User with username " + EXAMPLE_USER + " not found");
  }

  @Test
  public void addJoinedFandom_FandomDoesNotExist_ThrowsException() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        EXAMPLE_LEVEL, EXAMPLE_TYPE, EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    when(fandomRepository.findByFandomName(EXAMPLE_FANDOM_NAME)).thenReturn(null);

    assertThatExceptionOfType(FandomNotFoundException.class).isThrownBy(
        () -> fandomService.addJoinedFandom(request)
    ).withMessage("A fandom with the name " + EXAMPLE_FANDOM_NAME + " does not exist");
  }

  @Test
  public void addJoinedFandom_UserAlreadyJoinedFandom_ThrowsException() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        EXAMPLE_LEVEL, EXAMPLE_TYPE, EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    when(joinedRepository.findJoinedByUsernameAndFandomName(EXAMPLE_USER, EXAMPLE_FANDOM_NAME))
        .thenReturn(joined);

    assertThatExceptionOfType(UserAlreadyJoinedFandomException.class).isThrownBy(
        () -> fandomService.addJoinedFandom(request)
    ).withMessage("You have already joined " + EXAMPLE_FANDOM_NAME);
  }

  @Test
  public void addJoinedFandom_NewJoinedFandom_CorrectResponse() {
    AddJoinedFandomRequest request = new AddJoinedFandomRequest(
        EXAMPLE_LEVEL, EXAMPLE_TYPE, EXAMPLE_FANDOM_NAME, EXAMPLE_USER
    );

    AddJoinedFandomResponse response = fandomService.addJoinedFandom(request);

    verify(joinedRepository).save(any(Joined.class));
    assertThat(response.getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getType()).isEqualTo(EXAMPLE_TYPE);
  }

  @Test
  public void getFandomDetails_GetExistingFandom_CorrectResponse() {
    GetFandomDetailsResponse response = fandomService.getFandomDetails(EXAMPLE_FANDOM_NAME);
    assertThat(response.getDescription()).isEqualTo(EXAMPLE_DESCRIPTION);
    assertThat(response.getDisplayPhotoURL()).isEqualTo(EXAMPLE_DISPLAY_PHOTO_URL);
    assertThat(response.getFandomName()).isEqualTo(EXAMPLE_FANDOM_NAME);
    assertThat(response.getGenre()).isEqualTo(EXAMPLE_GENRE);
  }

  @Test
  public void getFandomDetail_GetNonExistingFandom_ThrowsException() {
    when(fandomRepository.findByFandomName("Example")).thenReturn(null);
    assertThatExceptionOfType(FandomNotFoundException.class)
        .isThrownBy(() -> fandomService.getFandomDetails("Example"))
        .withMessage("The fandom with the name, Example does not exist");
  }

  @Test
  public void leaveFandom_UserNotFoundException_ThrowsException() {
    LeaveFandomRequest request = new LeaveFandomRequest(EXAMPLE_FANDOM_NAME, EXAMPLE_USER);

    when(
            userRepository.findByUsername(EXAMPLE_USER)
    ).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> fandomService.leaveFandom(request))
        .withMessage("User with username " + EXAMPLE_USER + " does not exist");
  }

  @Test
  public void leaveFandom_FandomNotFoundException_ThrowsException() {
    LeaveFandomRequest request = new LeaveFandomRequest(EXAMPLE_FANDOM_NAME, EXAMPLE_USER);

    when(
            fandomRepository.findByFandomName(EXAMPLE_FANDOM_NAME)
    ).thenReturn(null);

    assertThatExceptionOfType(FandomNotFoundException.class)
        .isThrownBy(() -> fandomService.leaveFandom(request))
        .withMessage("Fandom with name " + EXAMPLE_FANDOM_NAME + " does not exist");
  }

  @Test
  public void leaveFandom_UserNotInFandomException_ThrowsException() {
    LeaveFandomRequest request = new LeaveFandomRequest(EXAMPLE_FANDOM_NAME, EXAMPLE_USER);

    assertThatExceptionOfType(UserNotInFandomException.class)
            .isThrownBy(() -> fandomService.leaveFandom(request))
            .withMessage("User is not in " + EXAMPLE_FANDOM_NAME);
  }

  @Test
  public void leaveFandom_RelationExists_CorrectMethodCalled() {
    LeaveFandomRequest request = new LeaveFandomRequest(EXAMPLE_FANDOM_NAME, EXAMPLE_USER);

    when(
            joinedRepository.findJoinedByUsernameAndFandomName(EXAMPLE_USER, EXAMPLE_FANDOM_NAME)
    ).thenReturn(joined);
    fandomService.leaveFandom(request);

    verify(joinedRepository).delete(joined);
  }
}
