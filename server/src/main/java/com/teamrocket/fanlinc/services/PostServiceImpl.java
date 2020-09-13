package com.teamrocket.fanlinc.services;

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
import com.teamrocket.fanlinc.models.PostUserPair;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

  private PostRepository postRepository;
  private FandomRepository fandomRepository;
  private UserRepository userRepository;
  private JoinedRepository joinedRepository;
  private static final List<String> levels = Arrays.asList("1", "2", "3", "4");
  private static final List<String> types = Arrays.asList("General", "Cosplayer", "Vendor/Artist");

  public PostServiceImpl(PostRepository postRepository, FandomRepository fandomRepository,
      UserRepository userRepository, JoinedRepository joinedRepository) {
    this.postRepository = postRepository;
    this.fandomRepository = fandomRepository;
    this.userRepository = userRepository;
    this.joinedRepository = joinedRepository;
  }

  /**
   * Finds all posts made by a user with the specified username and returns an object of all the
   * posts.
   *
   * @param username username of user whose posts are being retrieved
   * @return a {@link GetPostsResponse} object of all posts made by that user
   * @throws UserNotFoundException if the username is not valid
   */
  public GetPostsResponse getPostsByUser(String username) {

    User requestedUser = userRepository.findByUsername(username);
    if (requestedUser == null) {
      throw new UserNotFoundException("User with username " + username + " not found");
    }

    List<Post> posts = postRepository.findByPostedByOrderByPostedTimeDesc(username);

    // define list to store all user objects
    List<PostUserPair> postsAndUsers = generatePostUserPairList(posts);
    return new GetPostsResponse(postsAndUsers);
  }

  /**
   * Creates a post with the given info in the request, which contains the content, title, user,
   * date, Url for the photo, type, and Fandom of the post
   *
   * @param request a {@link AddPostRequest} object containing the information for the new post
   * @return a {@link AddPostResponse} object containing the title of the post, the user who posted
   * the post, and the Fandom the post was posted to
   * @throws FandomNotFoundException if requested Fandom was not found
   * @throws InvalidLevelException if requested level is invalid
   * @throws InvalidTypeException if requested type is invalid
   * @throws UserNotFoundException if requested User was not found
   * @throws UserNotInFandomException if the user is not in the fandom the post was being posted to
   */
  @Transactional()
  public AddPostResponse addPost(AddPostRequest request) {
    Fandom requestedFandom = fandomRepository.findByFandomName(request.getFandomName());
    // ensure the requested fandom has already been created
    if (requestedFandom == null) {
      // Fandom has not been created yet
      throw new FandomNotFoundException(
          "Fandom with name " + request.getFandomName() + " does not exist");
    }
    // Check if the requested level and type are valid
    if (!levels.contains(request.getLevel())) {
      throw new InvalidLevelException("Level must be one of " + levels.toString());
    }
    if (!types.contains(request.getType())) {
      throw new InvalidTypeException("Type must be one of " + types.toString());
    }
    User requestedUser = userRepository.findByUsername(request.getPostedBy());
    // ensure the requested username exists
    if (requestedUser == null) {
      throw new UserNotFoundException(
          "User with username " + request.getPostedBy() + " does not exist");
    }
    Joined requestedRelation =
        joinedRepository.findJoinedByUsernameAndFandomName(
            request.getPostedBy(), request.getFandomName());
    // ensure the user is already a member of the fandom
    if (requestedRelation == null) {
      // User not a member of the fandom, output exception
      throw new UserNotInFandomException("User not in " + request.getFandomName());
    }
    postRepository.save(
        new PostBuilder()
            .content(request.getContent())
            .fandomName(request.getFandomName())
            .level(request.getLevel())
            .title(request.getTitle())
            .postedBy(request.getPostedBy())
            .postedTime(new Date())
            .postPhotoURL(request.getPostPhotoURL())
            .type(request.getType())
            .build());

    // returns an instantiation of the response object you defined
    return new AddPostResponse(request.getTitle(), request.getPostedBy(), request.getFandomName());
  }

  /**
   * Finds all posts in a given fandom based on a given filter, if no filter is specified and the
   * keyword noFilter is passed in for both level and type this method will find all posts for a
   * given fandom. If only one filter is specified it will find posts based on the given filter and
   * fandom.
   *
   * @param fandomName the name of the fandom whose posts will be retrieved
   * @param level the level of the posts to be retrieved (or "noFilter" if level is not being
   * filtered)
   * @param type the type of the posts to be retrieved (or "noFilter" if type is not being
   * filtered)
   * @return a {@link GetPostsResponse} object containing the list of all posts matching the filters
   * and the corresponding users
   * @throws FandomNotFoundException if the specified fandom does not exist
   * @throws InvalidLevelException if the level specified is not 1,2,3,4 or noFilter
   * @throws InvalidTypeException if the type specified is not "General", "Cosplayer",
   * "Vendor/Artist" or "noFilter"
   */
  public GetPostsResponse getFilteredPosts(String fandomName, String level, String type) {

    // check if the requested fandom exists
    Fandom requestedFandom = fandomRepository.findByFandomName(fandomName);
    if (requestedFandom == null) {
      throw new FandomNotFoundException("Fandom with the name " + fandomName + " does not exist");
    }
    if (!levels.contains(level) && !level
        .equals("noFilter")) { // check if the requested level and type are valid
      throw new InvalidLevelException("Level must be one of " + levels.toString() + " or noFilter");
    }
    if (!types.contains(type) && !type.equals("noFilter")) { // check if the given type is valid
      throw new InvalidTypeException("Type must be one of " + types.toString() + " or noFilter");
    }

    // if requested parameters are valid check if any filter were provided
    List<Post> posts;
    if (level.equals("noFilter") && type.equals("noFilter")) {
      // if no filters were provided just return all posts for the given fandom
      posts = postRepository.findByFandomNameOrderByPostedTimeDesc(fandomName);
    } else if (level.equals("noFilter")) {
      posts = postRepository.findByFandomNameAndTypeOrderByPostedTimeDesc(fandomName, type);
    } else if (type.equals("noFilter")) {
      posts = postRepository.findByFandomNameAndLevelOrderByPostedTimeDesc(fandomName, level);
    } else {
      posts =
          postRepository.findByFandomNameAndLevelAndTypeOrderByPostedTimeDesc(
              fandomName, level, type);
    }
    // define list to store all user objects
    List<PostUserPair> postsAndUsers = generatePostUserPairList(posts);
    return new GetPostsResponse(postsAndUsers);
  }

  /**
   * Creates a list of posts and the users that posted them
   *
   * @param posts a list of {@link Post} objects
   * @return a {@link List<PostUserPair>} a List of posts and the user the posted them
   * @throws UserNotFoundException if a user for a given could not be found in the database
   */
  private List<PostUserPair> generatePostUserPairList(List<Post> posts) {
    List<PostUserPair> postsAndUsers = new ArrayList<>();
    for (Post post : posts) {
      User curr_user = userRepository.findByUsername(post.getPostedBy());
      if (curr_user == null) {
        throw new UserNotFoundException("The user with username: " + curr_user
            + " was not found, Invalid postedBy parameter for post entity");
      }
      postsAndUsers.add(new PostUserPair(post, curr_user));
    }
    return postsAndUsers;
  }

  /**
   * Finds a post given a username and the time it was posted as a key and deletes it.
   *
   * @param request a {@link DeletePostRequest} object containing the username of the user who made
   * the post and the time the post was posted
   * @throws PostNotFoundException if the specified post does not exist
   */
  public void deletePost(DeletePostRequest request) {
    String postedBy = request.getPostedBy();
    Date postedTime = request.getPostedTime();

    Post post = postRepository.findByPostedByAndPostedTime(postedBy, postedTime);
    if (post == null) {
      throw new PostNotFoundException(
          "Post from user, "
              + postedBy
              + " posted at, "
              + postedTime.toString()
              + " was not found");
    }
    postRepository.delete(post);
  }

  /**
   * Finds a post given a username and the time it was posted as a key and edits the requested
   * parameters to the value specified.
   *
   * @param request a {@link EditPostRequest} object containing the username of the user who made
   * the post, the time the post was posted, and fields to be edited along with the new values for
   * those fields
   * @return a {@link EditPostResponse} object containing the username, date and fields changed of
   * the edited post
   * @throws PostNotFoundException if the specified post does not exist
   * @throws InvalidLevelException if the level specified is not 1,2,3,4 or noFilter
   * @throws InvalidTypeException if the type specified is not "General", "Cosplayer",
   * "Vendor/Artist" or "noFilter"
   * @throws InvalidEditException if title, fandom, level or type are passed in as empty or invalid
   * strings
   * @throws FandomNotFoundException if a fandom that does not exist is requested
   * @throws UserNotInFandomException if the given user is not in the requested fandom
   */
  public EditPostResponse editPost(EditPostRequest request) {
    // relevant post based on username and time it was posted
    Post originalPost = postRepository
        .findByPostedByAndPostedTime(request.getPostedBy(), request.getPostedTime());
    // check if the requested post exists
    if (originalPost == null) {
      throw new PostNotFoundException(
          "Post from user, " + request.getPostedBy() + " posted at, " + request.getPostedTime()
              .toString() + " was not found");
    }
    // define a list of modified fields
    List<String> modifiedFields = new ArrayList<>();

    // ensure title level and type are not empty
    // check which properties need to be changed and change if they need to
    if (("").equals(request.getTitle())) {
      throw new InvalidEditException("Title cannot be an empty string");

    } else if (request.getTitle() != null) {
      originalPost.setTitle(request.getTitle());
      modifiedFields.add("title");
    }

    if (("").equals(request.getFandom())) {
      throw new InvalidEditException("Fandom cannot be an empty string");
    }

    if (request.getFandom() != null) {
      // check if the given fandom exists
      Fandom requestedFandom = fandomRepository.findByFandomName(request.getFandom());
      if (requestedFandom == null) {
        throw new FandomNotFoundException(
            "Fandom with the name: " + request.getFandom() + " does not exist");
      }

      // if the fandom exists and the user has joined the fandom then make the edit
      originalPost.setFandomName(request.getFandom());
      modifiedFields.add("fandom");
    }

    if (request.getPostPhotoURL() != null) {
      originalPost.setPostPhotoUrl(request.getPostPhotoURL());
      modifiedFields.add("postPhotoURL");
    }

    if (request.getContent() != null) {
      originalPost.setContent(request.getContent());
      modifiedFields.add("content");
    }

    if (request.getLevel() != null && !levels.contains(request.getLevel())) {
      // ensure the level passed in is a valid level
      throw new InvalidLevelException("Level must be one of " + levels.toString());

    } else if (request.getLevel() != null) {
      originalPost.setLevel(request.getLevel());
      modifiedFields.add("level");
    }

    if (request.getType() != null && !types.contains(request.getType())) {
      // ensure the type passed in is a valid type
      throw new InvalidTypeException("Type must be one of " + types.toString());

    } else if (request.getType() != null) {
      originalPost.setType(request.getType());
      modifiedFields.add("type");
    }

    postRepository.save(originalPost);
    return new EditPostResponse(originalPost.getPostedBy(), originalPost.getPostedTime(),
        modifiedFields);

  }
}
