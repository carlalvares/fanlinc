# Fanlinc

Fanlinc is a social network for cosplayers and fans. Users are able to create accounts, make profiles, connect with eachother and share posts to a newsfeed. It is built with a Java + Spring back-end, a Neo4j graph database, and a JavaScript + React front-end.

## Requirements

You must have [Neo4j](https://neo4j.com), [Java Development Kit 1.8](https://www.oracle.com/ca-en/java/technologies/javase/javase-jdk8-downloads.html)/[OpenJDK 8](https://adoptopenjdk.net) and npm (which comes with [Node.js](https://nodejs.org/en/)) installed.

## Usage

1. (Optional) Load the pre-populated database from `db/graph.db.dump`. You can find instructions for that [here](https://neo4j.com/docs/operations-manual/current/tools/dump-load/). Alternatively, you can just use a fresh Neo4j database.
2. Start Neo4j using `neo4j start`.
3. Start server application through Maven using `./mvnw spring-boot:run` in the `server/` directory.
4. Install the client application's dependencies using `npm install` in the `client/` directory. Then, start client application using `npm start` in the same directory.
5. Visit `localhost:3000` in your browser to access Fanlinc. Enjoy!
