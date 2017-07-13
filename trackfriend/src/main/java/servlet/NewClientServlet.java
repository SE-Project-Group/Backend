package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.ClientNeo4j;
import net.sf.json.JSONObject;
import service.impl.Neo4jServiceImpl;
import tool.GetRequestBody;


public class NewClientServlet extends HttpServlet{
	/**
	 * 
	 */
	private Neo4jServiceImpl neo4jService=new Neo4jServiceImpl();
	
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String s=GetRequestBody.getBody(request);
		JSONObject obj=JSONObject.fromObject(s);
		int client_id=obj.getInt("user_id");
    	ClientNeo4j clientNeo4j = new ClientNeo4j(client_id);			
    	neo4jService.create(clientNeo4j);	
    	PrintWriter out = response.getWriter();
    	out.print("success");
	}
}
