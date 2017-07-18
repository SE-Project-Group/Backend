package restful;

import model.Client;
import model.Feed;
import model.ReturnFeed;
import model.ReturnFollow;
import model.ReturnUserInfo;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	@Path("/clientLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public String clientLogin(
			@QueryParam("user_name") String userName,
    		@QueryParam("password") String password) 
	{
		Token token=appService.clientLogin(userName,password);
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
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		String uri="track/rest/app/clientLogout";
		if(appService.checkSign(userId,uri,sign)){
			appService.logout(userId);
			return "success";
		}
		return "error";
	}
	

	@POST
	@Path("/clientSignup")
	@Consumes(MediaType.APPLICATION_JSON)  
	@Produces("text/html")
	public String clientSignup(String message) throws JSONException{
		JSONObject obj = JSONObject.fromObject(message);
		int flag=appService.signup((String)obj.get("user_name"),(String)obj.get("password"),(String)obj.get("phone"));
		if(flag==0)return "success";
		else if(flag==1)return "existing phone";
		else if(flag==2)return "existing user name";
		else return "existing phone and user name";
	}
	
	@GET
	@Path("/queryPersonalInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String queryPersonalInfo(
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign) throws ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String uri="track/rest/app/queryPersonalInfo";
		if(sign==null)return null;
		if(!appService.checkSign(userId,uri,sign)){
			return null;
		}
		Client client=appService.getClientById(userId);
		if(client==null)return null;
		String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 
		JSONObject json = JSONUtil.toJson(client,processors);
		return json.toString();
	}
	
	@PUT
	@Path("/modifyPersonalInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modifyPersonalInfo(String message,
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign) throws JSONException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String uri="track/rest/app/modifyPersonalInfo";
		if(sign==null)return null;
		if(!appService.checkSign(userId,uri,sign)){
			return null;
		}
		JSONObject obj=JSONObject.fromObject(message);
		Client client=appService.getClientByUserName(obj.getString("user_name"));
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
    @Path("/newFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String newFeed(String feedInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(userId, "track/rest/app/newFeed", sign))return "status wrong";
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedInfo,Feed.class);
		 appService.newFeed(feed);
		 String res= "success";
		 return res;
     }
	@PUT
    @Path("/updateFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String updateFeed(String feedinfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign ) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(userId, "track/rest/app/updateFeed", sign))return "status wrong"; 
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedinfo,Feed.class);	 
		 appService.updateFeed(feed);
		 return "success";
     }
	@DELETE
    @Path("/removeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String removeFeed(String feedInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign ) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(userId, "track/rest/app/removeFeed", sign))return "status wrong";  
			JSONObject newfeed = JSONObject.fromObject(feedInfo);
			String _id= newfeed.getString("_id");
			appService.removeFeed(_id);
			return "success";
     }
	
	@GET
	@Path("/myFeed")
	@Produces("text/html")
	public  String myFeed(
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign )throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		 if(!appService.checkSign(userId, "track/rest/app/myFeed", sign))return "status wrong";  
		 List<Feed> feeds=appService.findFeedByUserId(userId);
		 List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		 return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("getFeedFromTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFeedFromTime(
			@QueryParam("time") String time,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFeedFromTime", sign))return "status wrong";
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=appService.findPublicFeedsByTime(ts);
		return JSONArray.fromObject(list).toString();
	}
	
	@GET
	@Path("/feedAround")
	@Produces(MediaType.APPLICATION_JSON)
	public String feedAround(
			@QueryParam("longitude") double longitude,
			@QueryParam("latitude") double latitude,
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(userId, "track/rest/app/feedAround", sign))return "status wrong"; 
		List<ReturnFeed>feeds=appService.findFeedAround(longitude, latitude, 10);
		return JSONArray.fromObject(feeds).toString();
	}
	
	 @PUT
     @Path("/incLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String incLikeFeed(String feedInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(userId, "track/rest/app/incLikeFeed", sign))return "status wrong"; 
		 JSONObject newfeed = JSONObject.fromObject(feedInfo);
		 String _id= newfeed.getString("_id");
		 int user_id=newfeed.getInt("user_id");
		 appService.incLikeFeed(_id,user_id);
		 return "success";
     }
	
	@PUT
	@Path("/newComment")
	@Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	public String newComment(String commentInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(userId, "track/rest/app/newComment", sign))return "status wrong"; 
		JSONObject newfeed=JSONObject.fromObject(commentInfo);
		String _id= newfeed.getString("_id");
		int id=newfeed.getInt("user_id");
		String text=newfeed.getString("text");
		int replyId=newfeed.getInt("reply_id");
		appService.newComment( _id, id, text,  replyId);
		return "success";
	}
	
	@GET
	@Path("getFriendFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFriendFeedList(
			@QueryParam("time") String time,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFriendFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=appService.getFriendFeedList(ts,userId);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("getAllFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getAllFeedList(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("time") String time)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getAllFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=appService.getAllFeedList(ts);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}

	@GET
	@Path("getFollowingFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFollowingFeedList(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("time") String time)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);
		List<Feed> feeds=appService.getFollowingFeedList(ts,userId);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("getMyFriendInformationById")

	@Produces("text/html")
	public String getMyFriendInformationById(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getMyFriendInformationById", sign))return "status wrong"; 
		List<Client> list=appService.getMyFriendInformationById(userId);
		if(list!=null)
		{ 
			String shortFormat = "yyyy-MM-dd";  
			Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
			processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 		
			JSONArray json = JSONUtil.toJsonArray(list,processors);
			return json.toString();
		}
		else return "error";
	}
	@GET
	@Path("getFollowingInformationById")

	@Produces("text/html")
	public String getFollowingInformationById(@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingInformationById", sign))return "status wrong"; 
		
		List<ReturnFollow> list=appService.getFollowingInformationById(userId);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	@GET
	@Path("getFollowerInformationById")

	@Produces("text/html")
	public String getFollowerInformationById(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		/*if(!appService.checkSign(userId, "track/rest/app/getFollowerInformationById", sign))return "status wrong"; */
	
		List<ReturnFollow> list=appService.getFollowerInformationById(userId);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	
	@GET
	@Path("getInfo")
	@Produces("text/html")
	public String getInfo(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("id") int id){
		/*if(!appService.checkSign(userId, "track/rest/app/getInfo", sign))return "status wrong"; */
		ReturnUserInfo rui=appService.getSomeoneInfo(id);
		JSONObject obj=JSONObject.fromObject(rui);
		return obj.toString();
	}
	
	@POST
	@Path("newFollow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String newFollow(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/newFollow", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));
		String res=appService.followSomeone(userId,followId);
		return res;
	}
	@DELETE
	@Path("deleteFollow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String deleteFollow(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/deleteFollow", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));	
		String res=appService.unFollowSomeone(userId,followId);
		return res;
	}
}

