
package com.teamrocket.fanlinc.services;

import com.teamrocket.fanlinc.requests.AddPostRequest;
import com.teamrocket.fanlinc.requests.DeletePostRequest;
import com.teamrocket.fanlinc.requests.EditPostRequest;
import com.teamrocket.fanlinc.responses.AddPostResponse;
import com.teamrocket.fanlinc.responses.EditPostResponse;
import com.teamrocket.fanlinc.responses.GetPostsResponse;

public interface PostService {

  GetPostsResponse getPostsByUser(String username);

  AddPostResponse addPost(AddPostRequest request);

  GetPostsResponse getFilteredPosts(String fandomName, String level, String type);

  void deletePost(DeletePostRequest request);

  EditPostResponse editPost(EditPostRequest request);

}