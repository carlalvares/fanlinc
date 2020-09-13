package com.teamrocket.fanlinc.builders;

import com.teamrocket.fanlinc.models.Post;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;

public class PostBuilder {
  private String title;
  private String content;
  private String level;
  private String type;
  private String postedBy;
  private String fandomName;
  @DateLong private Date postedTime;
  private String postPhotoURL;

  public PostBuilder title(String title) {
    this.title = title;
    return this;
  }

  public PostBuilder content(String content) {
    this.content = content;
    return this;
  }

  public PostBuilder level(String level) {
    this.level = level;
    return this;
  }

  public PostBuilder type(String type) {
    this.type = type;
    return this;
  }

  public PostBuilder postedBy(String postedBy) {
    this.postedBy = postedBy;
    return this;
  }

  public PostBuilder fandomName(String fandomName) {
    this.fandomName = fandomName;
    return this;
  }

  public PostBuilder postedTime(Date postedTime) {
    this.postedTime = postedTime;
    return this;
  }

  public PostBuilder postPhotoURL(String postPhotoURL) {
    this.postPhotoURL = postPhotoURL;
    return this;
  }

  public Post build() {
    Post newPost = new Post();
    newPost.setTitle(this.title);
    newPost.setContent(this.content);
    newPost.setFandomName(this.fandomName);
    newPost.setLevel(this.level);
    newPost.setPostedBy(this.postedBy);
    newPost.setPostedTime(this.postedTime);
    newPost.setPostPhotoUrl(this.postPhotoURL);
    newPost.setType(this.type);
    return newPost;
  }
}
