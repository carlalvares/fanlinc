package com.teamrocket.fanlinc.controllers;

import com.teamrocket.fanlinc.requests.AddUserRequest;
import com.teamrocket.fanlinc.responses.AddUserResponse;
import com.teamrocket.fanlinc.responses.UserDetailsResponse;
import com.teamrocket.fanlinc.responses.UserFandomsResponse;
import com.teamrocket.fanlinc.responses.ValidateUserResponse;
import com.teamrocket.fanlinc.services.AccountService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  private static final String BASE_PATH = "/account";

  private AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/validateUser", method = RequestMethod.GET)
  @ResponseBody
  public ValidateUserResponse validateUser(@RequestParam(name = "username") String username,
      @RequestParam(name = "password") String password) {
    return accountService.validateUser(username, password);
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/addUser", method = RequestMethod.POST)
  @ResponseBody
  public AddUserResponse addUser(@Valid @RequestBody AddUserRequest request) {
    return accountService.addUser(request);
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/userDetails", method = RequestMethod.GET)
  @ResponseBody
  public UserDetailsResponse getUserDetails(@RequestParam(name = "username") String username) {
    return accountService.getUserDetails(username);
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/userFandoms", method = RequestMethod.GET)
  @ResponseBody
  public UserFandomsResponse getUserFandoms(@RequestParam(name = "username") String username) {
    return accountService.getUserFandoms(username);
  }
}
