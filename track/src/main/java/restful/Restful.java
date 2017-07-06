package restful;

import model.Client;
import model.Feed;
import model.Location;
import model.ReturnFeed;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
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


import com.google.gson.Gson;

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
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedinfo,Feed.class);
		 appService.NewFeed(feed);
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
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedinfo,Feed.class);
		 
		 appService.UpdateFeed(feed);
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
		 
		 String _id= newfeed.getString("_id");
		 appService.removeFeed(_id);
		 String res= "success";
	 return res;
     }
	@GET
	@Path("/MyFeed")
	 @Produces("text/html")
	public  String MyFeed(@QueryParam("user_id") int user_id)throws JSONException
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
			@QueryParam("latitude") double latitude ){
		List<Feed>feeds=appService.findFeedAround(longitude, latitude, 10);
		List<ReturnFeed> res=new ArrayList<ReturnFeed>();
		for(int i=0;i<feeds.size();i++){
			Feed curFeed=feeds.get(i);
			ReturnFeed returnFeed=new ReturnFeed();
			String feed_owner=appService.getClientByID(curFeed.getUser_id()).getUser_name();
			returnFeed.setComment_cnt(curFeed.getCommentCount());
			returnFeed.setFeed_owner(feed_owner);
			returnFeed.setLatitude(curFeed.getLocation().getLatitude());
			returnFeed.setLongitude(curFeed.getLocation().getLongitude());
			returnFeed.setLike_cnt(curFeed.getLikeCount());
			returnFeed.setPic_id_list(curFeed.getPicList());
			returnFeed.setPosition(curFeed.getPosition());
			returnFeed.setShare_cnt(curFeed.getShareCount());
			returnFeed.setText(curFeed.getText());
			returnFeed.setDate(curFeed.getTime());
			returnFeed.setUser_ID(curFeed.getUser_id());
			res.add(returnFeed);
		}
		return JSONArray.fromObject(res).toString();
	}
	
	@POST
    @Path("/incLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	//return 0:ok   1:phone  2:user_name  3:phone&username
	 public String incLikeFeed(String feedinfo) throws JSONException{
		//String temp1[] =signinformation.split(",");
		 JSONObject newfeed = JSONObject.fromObject(feedinfo);

		 String _id= newfeed.getString("_id");
		 appService.incLikeFeed(_id);
		 String res= "success";
	 return res;
     }
	
}

