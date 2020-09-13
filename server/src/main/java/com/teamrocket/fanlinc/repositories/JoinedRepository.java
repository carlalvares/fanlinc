package com.teamrocket.fanlinc.repositories;

import com.teamrocket.fanlinc.models.Joined;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinedRepository extends Neo4jRepository<Joined, Long> {

  @Query("MATCH (a:User { username: {username} })-[r:JOINED]->(b:Fandom{fandomName:{fandomName}})"
          + "RETURN r")
  Joined findJoinedByUsernameAndFandomName(
      @Param("username") String username, @Param("fandomName") String fandomName);

  @Query("MATCH (a:User { username: {username} })-[r:JOINED]->(fandom) RETURN r, fandom")
  List<Joined> findJoinedByUsername(@Param("username") String username);
}
