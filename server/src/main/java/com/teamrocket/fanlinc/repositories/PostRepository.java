package com.teamrocket.fanlinc.repositories;

import com.teamrocket.fanlinc.models.Post;

import java.util.Date;
import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;


public interface PostRepository extends Neo4jRepository<Post, Long> {

  List<Post> findByFandomNameAndLevelAndTypeOrderByPostedTimeDesc(
      @Param("fandomName") String fandomName, @Param("level") String level,
      @Param("type") String type);

  List<Post> findByFandomNameAndLevelOrderByPostedTimeDesc(
      @Param("fandomName") String fandomName, @Param("level") String level);

  List<Post> findByFandomNameAndTypeOrderByPostedTimeDesc(
      @Param("fandomName") String fandomName, @Param("type") String type);

  List<Post> findByFandomNameOrderByPostedTimeDesc(@Param("fandomName") String fandomName);

  List<Post> findByPostedByOrderByPostedTimeDesc(@Param("postedBy") String postedBy);

  Post findByPostedByAndPostedTime(@Param("postedBy") String postedBy, @Param("postedTime") Date postedTime);
}
