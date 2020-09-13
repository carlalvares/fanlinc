package com.teamrocket.fanlinc.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teamrocket.fanlinc.builders.PostBuilder;
import com.teamrocket.fanlinc.exceptions.FandomNotFoundException;
import com.teamrocket.fanlinc.exceptions.InvalidEditException;
import com.teamrocket.fanlinc.exceptions.InvalidLevelException;
import com.teamrocket.fanlinc.exceptions.InvalidTypeException;
import com.teamrocket.fanlinc.exceptions.PostNotFoundException;
import com.teamrocket.fanlinc.exceptions.UserNotFoundException;
import com.teamrocket.fanlinc.exceptions.UserNotInFandomException;
import com.teamrocket.fanlinc.models.Fandom;
import com.teamrocket.fanlinc.models.Joined;
import com.teamrocket.fanlinc.models.Post;
import com.teamrocket.fanlinc.models.User;
import com.teamrocket.fanlinc.repositories.FandomRepository;
import com.teamrocket.fanlinc.repositories.JoinedRepository;
import com.teamrocket.fanlinc.repositories.PostRepository;
import com.teamrocket.fanlinc.repositories.UserRepository;
import com.teamrocket.fanlinc.requests.AddPostRequest;
import com.teamrocket.fanlinc.requests.DeletePostRequest;
import com.teamrocket.fanlinc.requests.EditPostRequest;
import com.teamrocket.fanlinc.responses.AddPostResponse;
import com.teamrocket.fanlinc.responses.EditPostResponse;
import com.teamrocket.fanlinc.responses.GetPostsResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostServiceImplTests {

  private static final String EXAMPLE_USER = "example";
  private static final String EXAMPLE_FANDOM = "fandom";
  private static final String EXAMPLE_TITLE = "title";
  private static final String EXAMPLE_TYPE = "General";
  private static final String EXAMPLE_LEVEL = "1";
  private static final Date EXAMPLE_POSTED_TIME = new Date(632282416);
  private static final String EXAMPLE_CONTENT = "content";
  private static final String EXAMPLE_PHOTO_URL = "http://www.example.com";
  private static final List<String> levels = Arrays.asList("1", "2", "3", "4");
  private static final List<String> types = Arrays.asList("General", "Cosplayer", "Vendor/Artist");
  private static final String NO_FILTER = "noFilter";
  private static final String NEW_TITLE = "title2";
  private static final String NEW_TYPE = "Cosplayer";
  private static final String NEW_LEVEL = "3";
  private static final String NEW_CONTENT = "content2";
  private static final String NEW_PHOTO_URL = "http://www.example2.com";
  private static final String NEW_FANDOM = "fandom2";

  @InjectMocks
  PostServiceImpl postService;

  @Mock
  PostRepository postRepository;

  @Mock
  FandomRepository fandomRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  JoinedRepository joinedRepository;

  private List<Post> posts;
  private Post examplePost;
  private User user;
  private Fandom fandom;
  private Joined joined;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    examplePost = new PostBuilder()
        .title(EXAMPLE_TITLE)
        .type(EXAMPLE_TYPE)
        .level(EXAMPLE_LEVEL)
        .postedBy(EXAMPLE_USER)
        .postedTime(EXAMPLE_POSTED_TIME)
        .fandomName(EXAMPLE_FANDOM)
        .content(EXAMPLE_CONTENT)
        .postPhotoURL(EXAMPLE_PHOTO_URL)
        .build();
    posts = new ArrayList<>();
    posts.add(examplePost);
    user = mock(User.class);
    fandom = mock(Fandom.class);
    joined = mock(Joined.class);
    when(userRepository.findByUsername(EXAMPLE_USER)).thenReturn(user);
    when(fandomRepository.findByFandomName(EXAMPLE_FANDOM)).thenReturn(fandom);
    when(fandomRepository.findByFandomName(NEW_FANDOM)).thenReturn(fandom);
    when(
        joinedRepository.findJoinedByUsernameAndFandomName(EXAMPLE_USER, EXAMPLE_FANDOM)
    ).thenReturn(joined);
    when(
        postRepository.findByPostedByAndPostedTime(EXAMPLE_USER, EXAMPLE_POSTED_TIME)
    ).thenReturn(examplePost);
  }

  @Test
  public void getPostsByUser_UserDoesNotExist_ThrowsException() {
    when(userRepository.findByUsername(EXAMPLE_USER)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> postService.getPostsByUser(EXAMPLE_USER)
    ).withMessage("User with username " + EXAMPLE_USER + " not found");
  }

  @Test
  public void getPostsByUser_UserExists_CorrectResponse() {
    when(postRepository.findByPostedByOrderByPostedTimeDesc(EXAMPLE_USER)).thenReturn(posts);

    GetPostsResponse response = postService.getPostsByUser(EXAMPLE_USER);

    assertThat(response.getPostsAndUsers().size()).isEqualTo(1);
    assertThat(response.getPostsAndUsers().get(0).getUser()).isEqualTo(user);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getPostsAndUsers().get(0).getPost().getContent()).isEqualTo(EXAMPLE_CONTENT);
    assertThat(response.getPostsAndUsers().get(0).getPost().getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().get(0).getPost().getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getPostsAndUsers().get(0).getPost().getType()).isEqualTo(EXAMPLE_TYPE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getTitle()).isEqualTo(EXAMPLE_TITLE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostPhotoUrl()).isEqualTo(EXAMPLE_PHOTO_URL);
  }

  @Test
  public void addPost_FandomDoesNotExist_ThrowsException() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        EXAMPLE_LEVEL,
        EXAMPLE_TYPE,
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    when(fandomRepository.findByFandomName(EXAMPLE_FANDOM)).thenReturn(null);

    assertThatExceptionOfType(FandomNotFoundException.class).isThrownBy(
        () -> postService.addPost(request)
    ).withMessage("Fandom with name " + EXAMPLE_FANDOM + " does not exist");
  }

  @Test
  public void addPost_InvalidLevel_ThrowsException() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        "wrong",
        EXAMPLE_TYPE,
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    assertThatExceptionOfType(InvalidLevelException.class).isThrownBy(
        () -> postService.addPost(request)
    ).withMessage("Level must be one of " + levels.toString());
  }

  @Test
  public void addPost_InvalidType_ThrowsException() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        EXAMPLE_LEVEL,
        "wrong",
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    assertThatExceptionOfType(InvalidTypeException.class).isThrownBy(
        () -> postService.addPost(request)
    ).withMessage("Type must be one of " + types.toString());
  }

  @Test
  public void addPost_UserDoesNotExist_ThrowsException() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        EXAMPLE_LEVEL,
        EXAMPLE_TYPE,
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    when(userRepository.findByUsername(EXAMPLE_USER)).thenReturn(null);

    assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(
        () -> postService.addPost(request)
    ).withMessage("User with username " + EXAMPLE_USER + " does not exist");
  }

  @Test
  public void addPost_UserHasNotJoinedFandom_ThrowsException() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        EXAMPLE_LEVEL,
        EXAMPLE_TYPE,
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    when(joinedRepository.findJoinedByUsernameAndFandomName(EXAMPLE_USER, EXAMPLE_FANDOM))
        .thenReturn(null);

    assertThatExceptionOfType(UserNotInFandomException.class).isThrownBy(
        () -> postService.addPost(request)
    ).withMessage("User not in " + EXAMPLE_FANDOM);
  }

  @Test
  public void addPost_NewPost_CorrectResponse() {
    AddPostRequest request = new AddPostRequest(
        EXAMPLE_TITLE,
        EXAMPLE_CONTENT,
        EXAMPLE_USER,
        EXAMPLE_LEVEL,
        EXAMPLE_TYPE,
        EXAMPLE_FANDOM,
        EXAMPLE_PHOTO_URL);

    AddPostResponse response = postService.addPost(request);

    verify(postRepository).save(any(Post.class));
    assertThat(response.getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getTitle()).isEqualTo(EXAMPLE_TITLE);
  }

  @Test
  public void getFilteredPosts_FandomDoesNotExist_ThrowsException() {
    when(fandomRepository.findByFandomName(EXAMPLE_FANDOM)).thenReturn(null);

    assertThatExceptionOfType(FandomNotFoundException.class).isThrownBy(
        () -> postService.getFilteredPosts(EXAMPLE_FANDOM, NO_FILTER, NO_FILTER)
    ).withMessage("Fandom with the name " + EXAMPLE_FANDOM + " does not exist");
  }

  @Test
  public void getFilteredPosts_InvalidLevel_ThrowsException() {
    assertThatExceptionOfType(InvalidLevelException.class).isThrownBy(
        () -> postService.getFilteredPosts(EXAMPLE_FANDOM, "wrong", NO_FILTER)
    ).withMessage("Level must be one of " + levels.toString() + " or noFilter");
  }

  @Test
  public void getFilteredPosts_InvalidType_ThrowsException() {
    assertThatExceptionOfType(InvalidTypeException.class).isThrownBy(
        () -> postService.getFilteredPosts(EXAMPLE_FANDOM, NO_FILTER, "wrong")
    ).withMessage("Type must be one of " + types.toString() + " or noFilter");
  }

  @Test
  public void getFilteredPosts_noFilters_CorrectMethodCalledAndCorrectResponse() {
    when(postRepository.findByFandomNameOrderByPostedTimeDesc(EXAMPLE_FANDOM)).thenReturn(posts);

    GetPostsResponse response = postService.getFilteredPosts(EXAMPLE_FANDOM, NO_FILTER, NO_FILTER);

    verify(postRepository).findByFandomNameOrderByPostedTimeDesc(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().size()).isEqualTo(1);
    assertThat(response.getPostsAndUsers().get(0).getUser()).isEqualTo(user);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getPostsAndUsers().get(0).getPost().getContent()).isEqualTo(EXAMPLE_CONTENT);
    assertThat(response.getPostsAndUsers().get(0).getPost().getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().get(0).getPost().getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getPostsAndUsers().get(0).getPost().getType()).isEqualTo(EXAMPLE_TYPE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getTitle()).isEqualTo(EXAMPLE_TITLE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostPhotoUrl()).isEqualTo(EXAMPLE_PHOTO_URL);
  }

  @Test
  public void getFilteredPosts_FilteredLevel_CorrectMethodCalledAndCorrectResponse() {
    when(
        postRepository.findByFandomNameAndLevelOrderByPostedTimeDesc(EXAMPLE_FANDOM, EXAMPLE_LEVEL)
    ).thenReturn(posts);

    GetPostsResponse response = postService.getFilteredPosts(
        EXAMPLE_FANDOM, EXAMPLE_LEVEL, NO_FILTER
    );

    verify(postRepository).findByFandomNameAndLevelOrderByPostedTimeDesc(
        EXAMPLE_FANDOM, EXAMPLE_LEVEL
    );
    assertThat(response.getPostsAndUsers().size()).isEqualTo(1);
    assertThat(response.getPostsAndUsers().get(0).getUser()).isEqualTo(user);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getPostsAndUsers().get(0).getPost().getContent()).isEqualTo(EXAMPLE_CONTENT);
    assertThat(response.getPostsAndUsers().get(0).getPost().getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().get(0).getPost().getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getPostsAndUsers().get(0).getPost().getType()).isEqualTo(EXAMPLE_TYPE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getTitle()).isEqualTo(EXAMPLE_TITLE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostPhotoUrl()).isEqualTo(EXAMPLE_PHOTO_URL);
  }

  @Test
  public void getFilteredPosts_FilteredType_CorrectMethodCalledAndCorrectResponse() {
    when(
        postRepository.findByFandomNameAndTypeOrderByPostedTimeDesc(EXAMPLE_FANDOM, EXAMPLE_TYPE)
    ).thenReturn(posts);

    GetPostsResponse response = postService.getFilteredPosts(
        EXAMPLE_FANDOM, NO_FILTER, EXAMPLE_TYPE
    );

    verify(postRepository).findByFandomNameAndTypeOrderByPostedTimeDesc(
        EXAMPLE_FANDOM, EXAMPLE_TYPE
    );
    assertThat(response.getPostsAndUsers().size()).isEqualTo(1);
    assertThat(response.getPostsAndUsers().get(0).getUser()).isEqualTo(user);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getPostsAndUsers().get(0).getPost().getContent()).isEqualTo(EXAMPLE_CONTENT);
    assertThat(response.getPostsAndUsers().get(0).getPost().getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().get(0).getPost().getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getPostsAndUsers().get(0).getPost().getType()).isEqualTo(EXAMPLE_TYPE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getTitle()).isEqualTo(EXAMPLE_TITLE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostPhotoUrl()).isEqualTo(EXAMPLE_PHOTO_URL);
  }

  @Test
  public void getFilteredPosts_FilteredTypeAndLevel_CorrectMethodCalledAndCorrectResponse() {
    when(
        postRepository.findByFandomNameAndLevelAndTypeOrderByPostedTimeDesc(
            EXAMPLE_FANDOM, EXAMPLE_LEVEL, EXAMPLE_TYPE
        )
    ).thenReturn(posts);

    GetPostsResponse response = postService.getFilteredPosts(
        EXAMPLE_FANDOM, EXAMPLE_LEVEL, EXAMPLE_TYPE
    );

    verify(postRepository).findByFandomNameAndLevelAndTypeOrderByPostedTimeDesc(
        EXAMPLE_FANDOM, EXAMPLE_LEVEL, EXAMPLE_TYPE
    );
    assertThat(response.getPostsAndUsers().size()).isEqualTo(1);
    assertThat(response.getPostsAndUsers().get(0).getUser()).isEqualTo(user);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedBy()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getPostsAndUsers().get(0).getPost().getContent()).isEqualTo(EXAMPLE_CONTENT);
    assertThat(response.getPostsAndUsers().get(0).getPost().getFandomName()).isEqualTo(EXAMPLE_FANDOM);
    assertThat(response.getPostsAndUsers().get(0).getPost().getLevel()).isEqualTo(EXAMPLE_LEVEL);
    assertThat(response.getPostsAndUsers().get(0).getPost().getType()).isEqualTo(EXAMPLE_TYPE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getTitle()).isEqualTo(EXAMPLE_TITLE);
    assertThat(response.getPostsAndUsers().get(0).getPost().getPostPhotoUrl()).isEqualTo(EXAMPLE_PHOTO_URL);
  }

  @Test
  public void deletePost_PostDoesNotExist_ThrowsException() {
    DeletePostRequest request = new DeletePostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);

    when(
        postRepository.findByPostedByAndPostedTime(EXAMPLE_USER, EXAMPLE_POSTED_TIME)
    ).thenReturn(null);

    assertThatExceptionOfType(PostNotFoundException.class).isThrownBy(
        () -> postService.deletePost(request)
    ).withMessage("Post from user, "
        + EXAMPLE_USER
        + " posted at, "
        + EXAMPLE_POSTED_TIME.toString()
        + " was not found");
  }

  @Test
  public void deletePost_PostExists_CorrectMethodCalled() {
    DeletePostRequest request = new DeletePostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);

    postService.deletePost(request);

    verify(postRepository).delete(examplePost);
  }

  @Test
  public void editPost_PostDoesNotExist_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    when(
        postRepository.findByPostedByAndPostedTime(EXAMPLE_USER, EXAMPLE_POSTED_TIME)
    ).thenReturn(null);

    assertThatExceptionOfType(PostNotFoundException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Post from user, "
        + EXAMPLE_USER
        + " posted at, "
        + EXAMPLE_POSTED_TIME.toString()
        + " was not found");
  }

  @Test
  public void editPost_EmptyTitle_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setTitle("");

    assertThatExceptionOfType(InvalidEditException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Title cannot be an empty string");
  }

  @Test
  public void editPost_NewTitle_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setTitle(NEW_TITLE);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getTitle()).isEqualTo(NEW_TITLE);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("title");
  }

  @Test
  public void editPost_NewPhotoURL_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setPostPhotoURL(NEW_PHOTO_URL);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getPostPhotoUrl()).isEqualTo(NEW_PHOTO_URL);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("postPhotoURL");
  }

  @Test
  public void editPost_NewContent_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setContent(NEW_CONTENT);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getContent()).isEqualTo(NEW_CONTENT);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("content");
  }

  @Test
  public void editPost_InvalidLevel_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setLevel("wrong");

    assertThatExceptionOfType(InvalidLevelException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Level must be one of " + levels.toString());
  }

  @Test
  public void editPost_NewLevel_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setLevel(NEW_LEVEL);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getLevel()).isEqualTo(NEW_LEVEL);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("level");
  }

  @Test
  public void editPost_InvalidType_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setType("wrong");

    assertThatExceptionOfType(InvalidTypeException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Type must be one of " + types.toString());
  }

  @Test
  public void editPost_NewType_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setType(NEW_TYPE);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getType()).isEqualTo(NEW_TYPE);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("type");
  }

  @Test
  public void editPost_EmptyFandom_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setFandom("");

    assertThatExceptionOfType(InvalidEditException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Fandom cannot be an empty string");
  }

  @Test
  public void editPost_FandomDoesNotExist_ThrowsException() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setFandom(NEW_FANDOM);

    when(fandomRepository.findByFandomName(NEW_FANDOM)).thenReturn(null);

    assertThatExceptionOfType(FandomNotFoundException.class).isThrownBy(
        () -> postService.editPost(request)
    ).withMessage("Fandom with the name: " + NEW_FANDOM + " does not exist");
  }

  @Test
  public void editPost_NewFandom_PostEditedAndCorrectResponse() {
    EditPostRequest request = new EditPostRequest(EXAMPLE_USER, EXAMPLE_POSTED_TIME);
    request.setFandom(NEW_FANDOM);

    EditPostResponse response = postService.editPost(request);

    verify(postRepository).save(examplePost);
    assertThat(examplePost.getFandomName()).isEqualTo(NEW_FANDOM);
    assertThat(response.getPostedTime()).isEqualTo(EXAMPLE_POSTED_TIME);
    assertThat(response.getUsername()).isEqualTo(EXAMPLE_USER);
    assertThat(response.getModifiedFields()).contains("fandom");
  }

}
