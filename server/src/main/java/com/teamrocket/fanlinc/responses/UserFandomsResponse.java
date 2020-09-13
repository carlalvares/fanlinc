package com.teamrocket.fanlinc.responses;

import com.teamrocket.fanlinc.models.UserFandomDetails;
import java.util.List;

public class UserFandomsResponse {

  private List<UserFandomDetails> userFandoms;

  public UserFandomsResponse(
      List<UserFandomDetails> userFandoms) {
    this.userFandoms = userFandoms;
  }

  public List<UserFandomDetails> getUserFandoms() {
    return userFandoms;
  }

  public void setUserFandoms(List<UserFandomDetails> userFandoms) {
    this.userFandoms = userFandoms;
  }
}
