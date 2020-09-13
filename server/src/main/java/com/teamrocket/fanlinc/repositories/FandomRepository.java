package com.teamrocket.fanlinc.repositories;

import com.teamrocket.fanlinc.models.Fandom;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface FandomRepository extends Neo4jRepository<Fandom, Long> {

  Fandom findByFandomName(@Param("fandomName") String fandomName);
}
