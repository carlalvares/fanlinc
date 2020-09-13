package com.teamrocket.fanlinc.services;

import com.teamrocket.fanlinc.requests.AddUserRequest;
import com.teamrocket.fanlinc.responses.AddUserResponse;
import com.teamrocket.fanlinc.responses.UserDetailsResponse;
import com.teamrocket.fanlinc.responses.UserFandomsResponse;
import com.teamrocket.fanlinc.responses.ValidateUserResponse;

public interface AccountService {

  ValidateUserResponse validateUser(String username, String password);

  AddUserResponse addUser(AddUserRequest request);

  UserDetailsResponse getUserDetails(String username);

  UserFandomsResponse getUserFandoms(String username);

}
