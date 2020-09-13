package com.teamrocket.fanlinc.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FandomServiceImpl implements FandomService {

  private JoinedRepository joinedRepository;
  private UserRepository userRepository;
  private FandomRepository fandomRepository;
  private static final List<String> levels = Arrays.asList("1", "2", "3", "4");
  private static final List<String> types = Arrays.asList("General", "Cosplayer", "Vendor/Artist");

  public FandomServiceImpl(
      JoinedRepository joinedRepository,
      UserRepository userRepository,
      FandomRepository fandomRepository) {
    this.joinedRepository = joinedRepository;
    this.userRepository = userRepository;
    this.fandomRepository = fandomRepository;
  }

  /**
   * Removes the joined relationship between fandom and user specified
   *
   * @param request a {@link LeaveFandomRequest} object containing the fandom the user wants to leave
   * @throws UserNotFoundException if User does not exist
   * @throws FandomNotFoundException if Fandom does not exist
   * @throws UserNotInFandomException if User has not joined requested fandom
   */
  @Transactional()
  public void leaveFandom(LeaveFandomRequest request) {
    User requestedUser = userRepository.findByUsername(request.getUsername());
    // ensure the requested username exists
    if (requestedUser == null) {
      throw new UserNotFoundException(
              "User with username " + request.getUsername() + " does not exist");
    }
    Fandom requestedFandom = fandomRepository.findByFandomName(request.getFandomName());
    // ensure the requested fandom has already been created
    if (requestedFandom == null) {
      // Fandom has not been created yet
      throw new FandomNotFoundException(
              "Fandom with name " + request.getFandomName() + " does not exist");
    }
    Joined requestedRelation =
            joinedRepository.findJoinedByUsernameAndFandomName(
                    request.getUsername(), request.getFandomName());

    // ensure the requested relation has already been created
    if (requestedRelation == null) {
      // if the requested relation isn't there output exception
      throw new UserNotInFandomException(
              "User is not in " + request.getFandomName());
    }

    // Relationship exists, delete it
    joinedRepository.delete(requestedRelation);
  }
  /**
   * Checks if a fandom with a given name exists in the database and if not it will create that
   * fandom with the given info
   *
   * @param request a {@link AddFandomRequest} object containing the information for the new fandom
   * @return a {@link AddFandomResponse} object containing the new fandoms name
   * @throws FandomAlreadyExistsException if a fandom with the requested name was already created
   */
  @Transactional()
  public AddFandomResponse addFandom(AddFandomRequest request) {
    Fandom requestedFandom = fandomRepository.findByFandomName(request.getFandomName());

    // ensure the requested fandom hasn't already been created
    if (requestedFandom != null) {
      // if the requested fandom isn't unique output exception
      throw new FandomAlreadyExistsException(
          "A fandom with the name " + request.getFandomName() + " already exists");
    }

    // if the fandom doesn't exist then create a new fandom node
    fandomRepository.save(
        new FandomBuilder()
            .fandomName(request.getFandomName()) // adds it to the database
            .description(request.getDescription())
            .genre(request.getGenre())
            .displayPhotoURL(request.getDisplayPhotoURL())
            .build());
    return new AddFandomResponse(request.getFandomName());
  }

  /**
   * Checks if level and type is one of the accepted values, fandom with given name and user with
   * given username exists and the user has not already joined the fandom, then join the user with
   * the fandom
   *
   * @param request a {@link AddJoinedFandomRequest} object containing the information about the
   *                user and fandom relationship
   * @return a {@link AddJoinedFandomResponse} object containing the relationship type and level
   * @throws FandomNotFoundException          if a fandom with the requested name does not exist
   * @throws UserNotFoundException            if a user with the given username does not exist
   * @throws UserAlreadyJoinedFandomException if the user with given username already joined the
   *                                          fandom
   * @throws InvalidTypeException             if request type is not one of {"General", "Cosplayer",
   *                                          "Vendor/Artist"}
   * @throws InvalidLevelException            if request level is not one of {"1", "2", "3", "4"}
   */
  @Transactional()
  public AddJoinedFandomResponse addJoinedFandom(AddJoinedFandomRequest request) {

    // if requested type is not one of {"General", "Cosplayer", "Vendor/Artist"}, throw
    // InvalidTypeException
    if (!types.contains(request.getType())) {
      throw new InvalidTypeException("Type must be one of " + types.toString());
    }
    // if requested level is not one of {"1", "2", "3", "4"}, throw InvalidLevelException
    if (!levels.contains(request.getLevel())) {
      throw new InvalidLevelException("Level must be one of " + levels.toString());
    }

    User requestedUser = userRepository.findByUsername(request.getUsername());
    // if the repository method returns a null value, user with given username was not found
    if (requestedUser == null) {
      throw new UserNotFoundException("User with username " + request.getUsername() + " not found");
    }

    Fandom requestedFandom = fandomRepository.findByFandomName(request.getFandomName());
    // ensure the requested fandom exists
    if (requestedFandom == null) {
      // if the requested fandom does not exist
      throw new FandomNotFoundException(
          "A fandom with the name " + request.getFandomName() + " does not exist");
    }

    Joined requestedRelation =
        joinedRepository.findJoinedByUsernameAndFandomName(
            request.getUsername(), request.getFandomName());

    // ensure the user is not already a member of the fandom
    if (requestedRelation != null) {
      // if the user is already a member of the fandom output exception
      throw new UserAlreadyJoinedFandomException(
          "You have already joined " + request.getFandomName());
    }
    Joined joined =
        new JoinedBuilder()
            .fandom(requestedFandom)
            .user(requestedUser)
            .level(request.getLevel())
            .type(request.getType())
            .build();

    joinedRepository.save(joined);
    return new AddJoinedFandomResponse(request.getType(), request.getLevel());
  }

  /**
   * Given a fandom name, checks if the requested fandom exists and if it does return the fandoms
   * details
   *
   * @param fandomName {@link String} - the requested fandoms name
   * @return {@link GetFandomDetailsResponse} - an object containing the fandoms name, genre,
   * description and photourl
   * @throws FandomNotFoundException - if the requested fandom cannot be found
   */
  @Transactional
  public GetFandomDetailsResponse getFandomDetails(String fandomName) {
    Fandom fandom = fandomRepository.findByFandomName(fandomName);
    if (fandom == null) {
      throw new FandomNotFoundException(
          "The fandom with the name, " + fandomName + " does not exist");
    }
    // if the fandom was found output it's details
    return new GetFandomDetailsResponse(fandom.getFandomName(), fandom.getGenre(),
        fandom.getDescription(), fandom.getDisplayPhotoURL());
  }

}
