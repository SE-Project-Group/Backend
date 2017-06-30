package restful;

import model.Client;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import service.AppService;
import util.SpringContextUtil;


@Path("/app")
public class Restful {
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
	@GET  
	@Path("/hello")

    @Produces(MediaType.TEXT_PLAIN)  
    public String sayHello() {  
        return "Hello Jersey";  
    }  
	
	@GET
	@Path("/clientLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String clientLogin(
			@QueryParam("user_name") String user_name,
			@QueryParam("password") String password)
	{
		if(appService.clientLogin(user_name, password)){
			return "success";
		}
		else return "error";
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
	
	@GET
	@Path("/query_personal_info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String query_personal_info(
			@QueryParam("user_name") String user_name)
	{
		Client client=appService.getClientByUser_name(user_name);
		if(client==null){
			return "username error!";
		}
		else return "success";
	}
	
	@PUT
	@Path("/modify_personal_info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String modify_personal_info(
			@QueryParam("user_name") String user_name,
			@QueryParam("password") String password,
			@QueryParam("birthday") Date birthday,
			@QueryParam("gender") String gender,
			@QueryParam("phone") String phone,
			@QueryParam("email") String email)
	{
		Client client=appService.getClientByUser_name(user_name);
		if(client==null){
			return "username error!";
		}
		else {
			client.setBirthday(birthday);
			client.setEmail(email);
			client.setGender(gender);
			client.setPassword(password);
			client.setPhone(phone);
			appService.updateClient(client);
			return "success";
		}
	}

}

