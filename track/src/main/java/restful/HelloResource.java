package restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Client;
import service.AppService;
import util.SpringContextUtil;

@Path("/test")  
public class HelloResource {  
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
    @GET  
    @Produces(MediaType.TEXT_PLAIN)  
    public String sayHello() {  
        return "Hello Jersey";  
    }  
    @GET  
    @Path("/hello")  
    @Produces(MediaType.TEXT_PLAIN)  
    public String sayanotherHello() {  
        return "AnotherHello Jersey";  
    }  
    @GET
	@Path("/clientLogin")
    @Produces(MediaType.TEXT_PLAIN)  
	public String clientLogin(
			@QueryParam("user_name") String user_name,
			@QueryParam("password") String password)
	{
		System.out.println("clientLogin");
	  boolean result=  appService.clientLogin(user_name,password);
	  String res=Boolean.toString(result);
	    return  res;
	}
    @POST
    @Path("/clientSignup")
	 //@Produces(MediaType.APPLICATION_JSON)
	 //@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
    public String clientSignup(
    		@QueryParam("user_name") String user_name,
    		@QueryParam("password") String password,
    		@QueryParam("phone") String phone){
		 System.out.println("addclient");
		 int flag=appService.checkSignup(user_name,password,phone);
		 String res = String.valueOf(flag);
		 if(flag==0){
			 Client client=new Client();
			 client.setUser_name(user_name);
			 client.setPassword(password);
			 client.setPhone(phone);
			 appService.insertClient(client);
			 return res;
		 }
		 else return res;
    }
}