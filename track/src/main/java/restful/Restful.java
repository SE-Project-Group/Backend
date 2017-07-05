package restful;

import model.Client;
import model.Feed;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;  
import org.springframework.data.mongodb.core.query.Query;

import service.AppService;
import util.JSONUtil;
import util.SQLDateProcessor;
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
	@Produces(MediaType.APPLICATION_JSON)
	public String clientLogin(
			@QueryParam("user_name") String user_name,
    		@QueryParam("password") String password) 
	{
		Token token=appService.clientLogin(user_name,password);
		if(token==null){
			return "ERROR";
		}
		JSONObject json=JSONObject.fromObject(token);
		return json.toString();
	}

	
	@GET
	@Path("/clientLogout")
	@Produces(MediaType.TEXT_PLAIN)
	public String clientLogout(
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException 
	{
		String uri="rest/app/clientLogout";
		if(sign==null)return null;
		if(appService.checkSign(user_ID,uri,sign)){
			appService.logout(user_ID);
			return "success";
		}
		return "error";
	}
	

	@POST
	    @Path("/clientSignup")
		 //@Produces(MediaType.APPLICATION_JSON)
		 //@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	
	   @Consumes(MediaType.APPLICATION_JSON)  
		 @Produces("text/html")
		//return 0:ok   1:phone  2:user_name  3:phone&username
	    public String clientSignup(String sign) throws JSONException{
			//String temp1[] =signinformation.split(",");
			 JSONObject signinformation = JSONObject.fromObject(sign);
			Client client=new Client();
			 client.setUser_name((String) signinformation.get("user_name"));
			 client.setPassword((String) signinformation.get("password"));
			 client.setPhone((String) signinformation.get("phone"));
			 appService.insertClient(client);
			// appService.insertClient(client);
			 String res= "success";
			
		 return res;
	     }
	/*@POST
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
	*/
	@GET
	@Path("/query_personal_info")
	@Produces(MediaType.APPLICATION_JSON)
	public String query_personal_info(
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws ClassNotFoundException, NoSuchAlgorithmException
	{
		String uri="rest/app/query_personal_info";
		if(sign==null)return null;
		if(!appService.checkSign(user_ID,uri,sign)){
			return null;
		}
		Client client=appService.getClientByID(user_ID);
		if(client==null)return null;
		String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 
		JSONObject json = JSONUtil.toJson(client,processors);
		return json.toString();
	}
	
	@PUT
	@Path("/modify_personal_info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modify_personal_info(String message,
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws JSONException, ParseException, NoSuchAlgorithmException
	{
		String uri="rest/app/modify_personal_info";
		if(sign==null)return null;
		if(!appService.checkSign(user_ID,uri,sign)){
			return null;
		}
		JSONObject obj=JSONObject.fromObject(message);
		Client client=appService.getClientByUser_name(obj.getString("user_name"));
		if(client==null){
			return "username error!";
		}
		else { 
			client.setBirthday(java.sql.Date.valueOf(obj.getString("birthday")));
			client.setEmail(obj.getString("email"));
			client.setGender(obj.getString("gender"));
			client.setPassword(obj.getString("password"));
			client.setPhone(obj.getString("phone"));
			appService.updateClient(client);
			return "success";
		}
	}
	@POST
    @Path("/NewFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
	 public String NewFeed(String feedinfo) throws JSONException{
		//String temp1[] =signinformation.split(",");
		 /*JSONObject newfeed = JSONObject.fromObject(feedinfo);
		 String list=newfeed.getString("mentionList");
		 String[] mention=list.split(",");
		 List<String> mentionlist = java.util.Arrays.asList(mention);
		 Feed feed=new Feed();
		 feed.setLongtitude(newfeed.getString("longtitude"));
		 feed.setMentionList(mentionlist);
		 feed.setUser_id(newfeed.getString("user_id"));
		 feed.setTime(System.currentTimeMillis());
		 feed.setLatitude(newfeed.getString("latitude"));
		 feed.setShareArea(newfeed.getString("shareArea"));
		 feed.setShowLocation(newfeed.getString("showLocation"));
		 feed.setText(newfeed.getString("text"));
		
		 appService.NewFeed(feed);*/
		 String res= "success";
		
	 return res;
     }
	@POST
    @Path("/UpdateFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
	 public String UpdateFeed(String feedinfo) throws JSONException{
		//String temp1[] =signinformation.split(",");
/*		 JSONObject newfeed = JSONObject.fromObject(feedinfo);
		 String list=newfeed.getString("mentionList");
		 String[] mention=list.split(",");
		 List<String> mentionlist = java.util.Arrays.asList(mention);
		 Feed feed=new Feed();
		 feed.setLongtitude(newfeed.getString("longtitude"));
		 feed.setMentionList(mentionlist);
		 feed.setUser_id(newfeed.getString("user_id"));
		 feed.setTime(System.currentTimeMillis());
		 feed.setLatitude(newfeed.getString("latitude"));
		 feed.setShareArea(newfeed.getString("shareArea"));
		 feed.setShowLocation(newfeed.getString("showLocation"));
		 feed.setText(newfeed.getString("text"));
		
		 appService.UpdateFeed(feed);*/
		 String res= "success";
		
	 return res;
     }
	@POST
    @Path("/RemoveFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
	 public String RemoveFeed(String feedinfo) throws JSONException{
		//String temp1[] =signinformation.split(",");
		 JSONObject newfeed = JSONObject.fromObject(feedinfo);
		 String user_id=newfeed.getString("user_id");
		 long time= Long.parseLong(newfeed.getString("time"));
		 appService.removeFeed(user_id,time);
		 String res= "success";
	 return res;
     }
	@GET
	@Path("/MyFeed")
	 @Produces("text/html")
	public  String MyFeed(@QueryParam("user_id") String user_id)throws JSONException
	{
		
		
		 List<Feed> list=appService.findFeedByUser_id(user_id);
		 JSONArray newfeed = JSONArray.fromObject(list);
		
	 return newfeed.toString();
	}
	
	@GET
	@Path("/feedAround")
	@Produces(MediaType.APPLICATION_JSON)
	public String feedAround(
			@QueryParam("longitude") double longitude,
			@QueryParam("latitude") double latitude,
			@QueryParam("radius") double radius ){
		List<Point>points=appService.findPointAround(longitude, latitude, radius);
		return JSONArray.fromObject(points).toString();
	}
}

