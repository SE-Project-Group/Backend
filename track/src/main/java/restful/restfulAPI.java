package restful;

import model.Client;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.AppService;
import util.SpringContextUtil;

@Path("/")
public class restfulAPI {
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
	@GET
	@Produces("text/html")
	public Response getStartingPage()
	{
		String output = "<h1>DOES THIS WORK?<h1>";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/clientLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean clientLogin(
			@QueryParam("user_name") String user_name,
			@QueryParam("password") String password)
	{
		System.out.println("clientLogin");
	    return appService.clientLogin(user_name,password);
	}
	
	@POST
    @Path("/clientSignup")
	 //@Produces(MediaType.APPLICATION_JSON)
	 //@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
    public int clientSignup(
    		@QueryParam("user_name") String user_name,
    		@QueryParam("password") String password,
    		@QueryParam("phone") String phone){
		 System.out.println("addclient");
		 int flag=appService.checkSignup(user_name,password,phone);
		 if(flag==0){
			 Client client=new Client();
			 client.setUser_name(user_name);
			 client.setPassword(password);
			 client.setPhone(phone);
			 appService.insertClient(client);
			 return 0;
		 }
		 else return flag;
    }
}
