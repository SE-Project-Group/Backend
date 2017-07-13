package service;

import org.springframework.data.neo4j.conversion.Result;

import model.ClientNeo4j;

public interface Neo4jService {
	/**
	 * 
	 * neo4j
	 */
	
	ClientNeo4j create(ClientNeo4j clientNeo4j);
    void delete(ClientNeo4j clientNeo4j);		
    ClientNeo4j findById(long id);		
    Result<ClientNeo4j> findAll();
    
}
