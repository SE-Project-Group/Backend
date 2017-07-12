package model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class ClientNeo4j {

   @GraphId 
   private Long id;
   private int client_id;
   
   public Long getId() {
	      return id;
   }		
   
   public int getClient_id() {
		return client_id;
	}
   public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
   public ClientNeo4j(){
	   
   }
   public ClientNeo4j(int client_id){
	   this.client_id=client_id;
   }
   public boolean equals(Object other) {
	   if (this == other)return true;
	   if (id == null) return false;
	   if (! (other instanceof ClientNeo4j)) return false;
	   return id.equals(((ClientNeo4j) other).id);
   }
	   
   public int hashCode() {
	   return id == null ? System.identityHashCode(this) : id.hashCode();
   }
	
}
