package restful;

import model.Client;
import model.Feed;
import model.ReturnFeed;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
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
		 System.out.println(feedInfo);
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedInfo,Feed.class);
		 appService.newFeed(feed);
		 String res= "success";
		 return res;
     }
	@POST
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
	@POST
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
		 List<Feed> list=appService.findFeedByUserId(userId);
		 JSONArray newfeed = JSONArray.fromObject(list);
		 return newfeed.toString();
	}
	
	@POST
	@Path("getFeedFromTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFeedFromTime(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFeedFromTime", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		String time=tsinfo.getString("time");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> list=appService.findPublicFeedsByTime(ts);
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
		List<Feed>feeds=appService.findFeedAround(longitude, latitude, 10);
		List<ReturnFeed> res=new ArrayList<ReturnFeed>();
		for(int i=0;i<feeds.size();i++){
			Feed curFeed=feeds.get(i);
			ReturnFeed returnFeed=new ReturnFeed();
			String feedOwner=appService.getClientById(curFeed.getUserId()).getUserName();
			returnFeed.set_id(curFeed.get_id());
			returnFeed.setComment_cnt(curFeed.getCommentCount());
			returnFeed.setFeed_owner(feedOwner);
			returnFeed.setLatitude(curFeed.getLocation().getLatitude());
			returnFeed.setLongitude(curFeed.getLocation().getLongitude());
			returnFeed.setLike_cnt(curFeed.getLikeCount());
			returnFeed.setPicCount(curFeed.getPicCount());
			returnFeed.setPosition(curFeed.getPosition());
			returnFeed.setShare_cnt(curFeed.getShareCount());
			returnFeed.setText(curFeed.getText());
			returnFeed.setDate(curFeed.getTime());
			returnFeed.setUser_ID(curFeed.getUserId());
			returnFeed.setLikeList(curFeed.getLikeList());
			returnFeed.setCommentList(curFeed.getCommentList());
			res.add(returnFeed);
		}
		return JSONArray.fromObject(res).toString();
	}
	
	 @POST
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
	
	@POST
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
	
	@POST
	@Path("getFriendFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFriendFeedList(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFriendFeedList", sign))return "Status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		String time=tsinfo.getString("time");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> list=appService.getFriendFeedList(ts,userId);
		return JSONArray.fromObject(list).toString();
	}

	@POST
	@Path("getFollowingFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFollowingFeedList(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingFeedList", sign))return "Status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		String time=tsinfo.getString("time");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		   ts=Timestamp.valueOf(time);
		List<Feed> list=appService.getFollowingFeedList(ts,userId);
		return JSONArray.fromObject(list).toString();
	}
	
	@GET
	@Path("getMyFriendInformationById")

	@Produces("text/html")
	public String getMyFriendInformationById(@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getMyFriendInformationById", sign))return "Status wrong"; 
		List<Client> list=appService.getMyFriendInformationById(userId);
		if(list!=null)
		{ 
			String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 		
		JSONArray json = JSONUtil.toJsonArray(list,processors);
	
		
	

		 return json.toString();}
		else 
			return "error";
	}
	@GET
	@Path("getMyFollowingInformationById")

	@Produces("text/html")
	public String getMyFollowingInformationById(@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getMyFollowingInformationById", sign))return "Status wrong"; 
		
		List<Client> list=appService.getMyFollowingInformationById(userId);
		if(list!=null)
		{ 
			String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 		
		JSONArray json = JSONUtil.toJsonArray(list,processors);
	
		
	

		 return json.toString();}
		else 
			return "error";
	}
	@GET
	@Path("getFollowingMeInformationById")

	@Produces("text/html")
	public String getFollowingMeInformationById(@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		/*if(!appService.checkSign(userId, "track/rest/app/getFollowingMeInformationById", sign))return "Status wrong"; */
	
		List<Client> list=appService.getFollowingMeInformationById(userId);
		if(list!=null)
		{ 
			String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 		
		JSONArray json = JSONUtil.toJsonArray(list,processors);
	
		
	

		 return json.toString();}
		else 
			return "error";
	}
	
	@POST
	@Path("newFollow")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String newFollow(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/newFollow", sign))return "Status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));
		
		String res=appService.followSomeone(userId,followId);
		return res;
	}
	@POST
	@Path("deleteFollow")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String deleteFollow(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/newFollow", sign))return "Status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));
		
		String res=appService.unFollowSomeone(userId,followId);
		return res;
	}
}

