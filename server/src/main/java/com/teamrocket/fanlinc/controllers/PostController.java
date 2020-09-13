package com.teamrocket.fanlinc.controllers;

import com.teamrocket.fanlinc.requests.AddPostRequest;
import com.teamrocket.fanlinc.requests.DeletePostRequest;
import com.teamrocket.fanlinc.requests.EditPostRequest;
import com.teamrocket.fanlinc.responses.AddPostResponse;
import com.teamrocket.fanlinc.responses.EditPostResponse;
import com.teamrocket.fanlinc.responses.GetPostsResponse;
import com.teamrocket.fanlinc.services.PostService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private static final String BASE_PATH = "/post";
  private PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/addPost", method = RequestMethod.POST)
  @ResponseBody
  public AddPostResponse addPost(@Valid @RequestBody AddPostRequest request) {
    return postService.addPost(request);
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/filteredPosts", method = RequestMethod.GET)
  @ResponseBody
  public GetPostsResponse getFilteredPosts(
      @RequestParam(name = "fandomName") String fandomName,
      @RequestParam(name = "level") String level,
      @RequestParam(name = "type") String type) {
    return postService.getFilteredPosts(fandomName, level, type);
  }

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = BASE_PATH + "/postsByUser", method = RequestMethod.GET)
  public GetPostsResponse getPostsByUser(@RequestParam(name = "username") String username) {
    return postService.getPostsByUser(username);
  }

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = BASE_PATH + "/editPost", method = RequestMethod.PATCH)
  public EditPostResponse editPost(@Valid @RequestBody EditPostRequest request) {
    return postService.editPost(request);
  }

  @CrossOrigin
  @RequestMapping(value = BASE_PATH + "/deletePost", method = RequestMethod.DELETE)
  public void deletePost(@Valid @RequestBody DeletePostRequest request) {
    postService.deletePost(request);
  }
}
