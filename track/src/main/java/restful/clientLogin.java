package restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import service.AppService;
import util.SpringContextUtil;
@Path("/clientLogin")
public class clientLogin {
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
	@GET

	@Produces(MediaType.APPLICATION_JSON)
	public boolean clientLogin(
			@QueryParam("user_name") String user_name,
			@QueryParam("password") String password)
	{
		System.out.println("clientLogin");
	    return appService.clientLogin(user_name,password);
	}
}
