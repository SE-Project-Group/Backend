package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.ClientNeo4j;
import service.Neo4jService;
public class mongo {
    public static void main(String[] args) {
    	ApplicationContext context = new ClassPathXmlApplicationContext("googleProfile.xml");		
    	Neo4jService neo4jService = (Neo4jService) context.getBean("neo4jService");
        ClientNeo4j clientNeo4j = createProfile();
        createProfile(neo4jService,clientNeo4j);		
        System.out.println("GoogleProfile created successfully.");
        
    }
    private static ClientNeo4j createProfile(Neo4jService neo4jService,ClientNeo4j clientNeo4j){
        return neo4jService.create(clientNeo4j);
     }	
    private static ClientNeo4j createProfile(){
    	ClientNeo4j clientNeo4j = new ClientNeo4j();			
        return clientNeo4j;
     }
}