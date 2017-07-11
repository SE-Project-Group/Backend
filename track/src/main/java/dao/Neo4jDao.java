package dao;

import org.springframework.data.neo4j.repository.GraphRepository;

import model.ClientNeo4j;

public interface Neo4jDao extends GraphRepository<ClientNeo4j>{ 
}
