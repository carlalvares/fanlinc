package com.teamrocket.fanlinc.services;

import com.teamrocket.fanlinc.requests.AddFandomRequest;
import com.teamrocket.fanlinc.requests.AddJoinedFandomRequest;
import com.teamrocket.fanlinc.requests.LeaveFandomRequest;
import com.teamrocket.fanlinc.responses.AddFandomResponse;
import com.teamrocket.fanlinc.responses.AddJoinedFandomResponse;
import com.teamrocket.fanlinc.responses.GetFandomDetailsResponse;

public interface FandomService {

  AddFandomResponse addFandom(AddFandomRequest request);

  AddJoinedFandomResponse addJoinedFandom(AddJoinedFandomRequest request);

  GetFandomDetailsResponse getFandomDetails(String fandomName);

  void leaveFandom(LeaveFandomRequest request);
}
