package com.teamrocket.fanlinc.responses;

import com.teamrocket.fanlinc.models.PostUserPair;


import java.util.List;

public class GetPostsResponse {

  private List<PostUserPair> postsAndUsers;

  public GetPostsResponse(List<PostUserPair> postsAndUsers) {
    this.postsAndUsers = postsAndUsers;
  }

  public List<PostUserPair> getPostsAndUsers() {
    return postsAndUsers;
  }

  public void setPostsAndUsers(List<PostUserPair> postsAndUsers) {
    this.postsAndUsers = postsAndUsers;
  }
}
