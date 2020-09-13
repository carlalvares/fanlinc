package com.teamrocket.fanlinc.services;

import com.teamrocket.fanlinc.builders.UserBuilder;
import com.teamrocket.fanlinc.builders.UserDetailsResponseBuilder;
import com.teamrocket.fanlinc.exceptions.UserNotFoundException;
import com.teamrocket.fanlinc.exceptions.UsernameNotUniqueException;
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
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

  private UserRepository userRepository;
  private JoinedRepository joinedRepository;

  public AccountServiceImpl(UserRepository userRepository, JoinedRepository joinedRepository) {
    this.userRepository = userRepository;
    this.joinedRepository = joinedRepository;
  }

  /**
   * Checks if user with given username is present in database, and if so, checks if the given
   * password matches the one stored for that user
   *
   * @param username username of given user
   * @param password password of given user
   * @return a {@link ValidateUserResponse} object containing the given username and whether or not
   * the given password matches the stored password
   * @throws UserNotFoundException if user with given username was not found
   */
  @Transactional(readOnly = true)
  public ValidateUserResponse validateUser(String username, String password) {
    User requestedUser = userRepository.findByUsername(username);
    // if the repository method returns a null value, user with given username was not found
    if (requestedUser == null) {
      throw new UserNotFoundException("User with username " + username + " not found");
    }
    // compare given password with password stored in the database and save this result in the
    // response object
    return new ValidateUserResponse(
        username, requestedUser.getPassword().equals(password));
  }

  /**
   * Checks if a username with a given username exists in the database and if not it will add the
   * user
   *
   * @param request a {@link AddUserRequest} object containing all user registration information
   * @return a {@link AddUserResponse} object containing the given username of the new user
   * @throws UsernameNotUniqueException if user with given username already exists
   */
  @Transactional()
  public AddUserResponse addUser(AddUserRequest request) {
    // check to see if the user with the given username already exists
    User requestedUser = userRepository.findByUsername(request.getUsername());
    // ensure the requested username is unique if it is not throw an exception
    if (requestedUser != null) {
      throw new UsernameNotUniqueException(
          "User with username " + request.getUsername() + " already exists");
    }
    // otherwise create a new user and save the user into the repo
    userRepository.save(
        new UserBuilder()
            .username(request.getUsername())
            .password(request.getPassword())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .dateOfBirth(request.getDateOfBirth())
            .bio(request.getBio())
            .location(request.getLocation())
            .profilePhotoUrl(request.getProfilePhotoUrl())
            .build());
    return new AddUserResponse(request.getUsername());
  }

  /**
   * Checks if a user with a given username exists in the database and if so it will return that
   * user's information
   *
   * @param username username of given user
   * @return a {@link UserDetailsResponse} object containing the requested users information
   * @throws UserNotFoundException if user with given username was not found
   */
  @Transactional(readOnly = true)
  public UserDetailsResponse getUserDetails(String username) {
    // first check if the requested user exists
    User requestedUser = userRepository.findByUsername(username);

    // if the username doesn't exist throw an error
    if (requestedUser == null) {
      throw new UserNotFoundException("User with username " + username + " not found");
    }

    // otherwise return the requested users info
    return new UserDetailsResponseBuilder()
        .bio(requestedUser.getBio())
        .dateOfBirth(requestedUser.getDateOfBirth())
        .firstName(requestedUser.getFirstName())
        .lastName(requestedUser.getLastName())
        .location(requestedUser.getLocation())
        .profilePhotoUrl(requestedUser.getProfilePhotoUrl())
        .username(requestedUser.getUsername())
        .build();
  }

  /**
   * Checks if given user exist, if so returns all the fandom names, levels and types for the
   * fandoms the user has joined the user has joined
   *
   * @param username username of given user
   * @return a {@link UserFandomsResponse} object containing list of fandom names, levels and types
   * @throws UserNotFoundException if a user with the given username does not exist
   */
  @Transactional(readOnly = true)
  public UserFandomsResponse getUserFandoms(String username) {

    User requestedUser = userRepository.findByUsername(username);
    // if the repository method returns a null value, user with given username was not found
    if (requestedUser == null) {
      throw new UserNotFoundException("User with username " + username + " not found");
    }
    List<Joined> fandomRelations = joinedRepository.findJoinedByUsername(username);
    List<UserFandomDetails> userFandoms = new ArrayList<UserFandomDetails>();
    for (Joined fandom : fandomRelations) {
      userFandoms.add(new UserFandomDetails(fandom.getFandom().getFandomName(), fandom.getLevel(),
          fandom.getType()));
    }
    return new UserFandomsResponse(userFandoms);
  }
}
