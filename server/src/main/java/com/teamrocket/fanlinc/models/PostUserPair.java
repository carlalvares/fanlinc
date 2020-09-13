package com.teamrocket.fanlinc.models;


public class PostUserPair {

  private Post post;
  private User user;

  public PostUserPair(Post post, User user) {
    this.post = post;
    this.user = user;
  }

  public Post getPost() {
    return post;
  }

  public User getUser() {
    return user;
  }
}
