package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import dao.Neo4jDao;
import model.ClientNeo4j;
import service.Neo4jService;

@Service("neo4jService")
public class Neo4jServiceImpl implements Neo4jService{
	
	
	@Autowired
	private Neo4jDao neo4jDao;
	/**
	 * neo4j
	 */
	public ClientNeo4j create(ClientNeo4j clientNeo4j) {
		return neo4jDao.save(clientNeo4j);
	}

	public void delete(ClientNeo4j clientNeo4j) {		
		neo4jDao.delete(clientNeo4j);
	}

	public ClientNeo4j findById(long id) {		
	    return neo4jDao.findOne(id);
	}

	public Result<ClientNeo4j> findAll() {		
	    return neo4jDao.findAll();
	}

}
