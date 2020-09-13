package com.teamrocket.fanlinc.repositories;

import com.teamrocket.fanlinc.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Neo4jRepository<User, Long> {

  User findByUsername(@Param("username") String username);
}
