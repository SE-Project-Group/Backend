package restful;

import model.Client;
import model.Feed;
import model.Location;
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
	
	/*@GET  
	@Path("/hello")

    @Produces(MediaType.TEXT_PLAIN)  
    public String sayHello() {  
        return "Hello Jersey";  
    }  */
	
	@GET
	@Path("/ClientLogin")
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
	@Path("/ClientLogout")
	@Produces(MediaType.TEXT_PLAIN)
	public String clientLogout(
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		String uri="track/rest/app/clientLogout";
		if(appService.checkSign(user_ID,uri,sign)){
			appService.logout(user_ID);
			return "success";
		}
		return "error";
	}
	

	@POST
	@Path("/ClientSignup")
	@Consumes(MediaType.APPLICATION_JSON)  
	@Produces("text/html")
	public String clientSignup(String message) throws JSONException{
		JSONObject obj = JSONObject.fromObject(message);
		Client client=new Client();
		int flag=appService.signup((String)obj.get("user_name"),(String)obj.get("password"),(String)obj.get("phone"));
		if(flag==0)return "success";
		else if(flag==1)return "existing phone";
		else if(flag==2)return "existing user name";
		else return "existing phone and user name";
	}
	
	@GET
	@Path("/QueryPersonalInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String query_personal_info(
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String uri="track/rest/app/query_personal_info";
		if(sign==null)return null;
		if(!appService.checkSign(user_ID,uri,sign)){
			return "no sign";
		}
		Client client=appService.getClientByID(user_ID);
		if(client==null)return "no such client";
		String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 
		JSONObject json = JSONUtil.toJson(client,processors);
		return json.toString();
	}
	
	@PUT
	@Path("/ModifyPersonalInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modify_personal_info(String message,
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws JSONException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String uri="track/rest/app/modify_personal_info";
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
	 public String NewFeed(String feedinfo,
			 @QueryParam("user_ID") int user_ID,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(user_ID, "track/rest/app/NewFeed", sign))return "Status wrong";
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
	 public String UpdateFeed(String feedinfo,
			 @QueryParam("user_ID") int user_ID,
			 @QueryParam("sign") String sign ) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 if(!appService.checkSign(user_ID, "track/rest/app/UpdateFeed", sign))return "Status wrong"; 
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
	 public String RemoveFeed(String feedinfo,
			 @QueryParam("user_ID") int user_ID,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(user_ID, "track/rest/app/RemoveFeed", sign))return "Status wrong";  
		JSONObject newfeed = JSONObject.fromObject(feedinfo);
		 
		 String _id= newfeed.getString("_id");
		 appService.removeFeed(_id);
		 String res= "success";
	 return res;
     }
	@GET
	@Path("/MyFeed")
	 @Produces("text/html")
	public  String MyFeed(@QueryParam("user_ID") int user_ID,@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if(!appService.checkSign(user_ID, "track/rest/app/MyFeed", sign))return "Status wrong";  
		
		 List<Feed> list=appService.findFeedByUser_id(user_ID);
		 JSONArray newfeed = JSONArray.fromObject(list);
		
	 return newfeed.toString();
	}
	@POST
	@Path("GetFeedFromTime")
	@Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	public String GetFeedFromTime(String time,
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(user_ID, "track/rest/app/GetFeedFromTime", sign))return "Status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		 List<Feed> list=appService.findPublicFeedsByTime(ts);
		
		
	 return list.toString();
	}
	@GET
	@Path("/FeedAround")
	@Produces(MediaType.APPLICATION_JSON)
	public String feedAround(
			@QueryParam("longitude") double longitude,
			@QueryParam("latitude") double latitude,
			@QueryParam("user_ID") int user_ID,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(user_ID, "track/rest/app/FeedAround", sign))return "Status wrong"; 
		List<Feed>feeds=appService.findFeedAround(longitude, latitude, 10);
		List<ReturnFeed> res=new ArrayList<ReturnFeed>();
		for(int i=0;i<feeds.size();i++){
			Feed curFeed=feeds.get(i);
			ReturnFeed returnFeed=new ReturnFeed();
			System.out.print(curFeed.getUser_id());
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
			returnFeed.setLikeList(curFeed.getLikeList());
			returnFeed.setCommentList(curFeed.getCommentList());
			res.add(returnFeed);
		}
		return JSONArray.fromObject(res).toString();
	}
	
	@POST
    @Path("/IncLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String IncLikeFeed(String feedinfo,
			 @QueryParam("user_ID") int user_ID,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(user_ID, "track/rest/app/IncLikeFeed", sign))return "Status wrong"; 
		 JSONObject newfeed = JSONObject.fromObject(feedinfo);

		 String _id= newfeed.getString("_id");
		int user_id=newfeed.getInt("user_id");
		 appService.incLikeFeed(_id,user_id);
		 String res= "success";
	 return res;
     }
	
	@POST
	@Path("/NewComment")
	@Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	public String NewComment(String commentinfo,
			 @QueryParam("user_ID") int user_ID,
			 @QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		if(!appService.checkSign(user_ID, "track/rest/app/NewComment", sign))return "Status wrong"; 
		JSONObject newfeed=JSONObject.fromObject(commentinfo);
		String _id= newfeed.getString("_id");
		int user_id=newfeed.getInt("user_id");
		String text=newfeed.getString("text");
		int reply_id=newfeed.getInt("reply_id");
		 appService.NewComment( _id, user_id, text,  reply_id);
		 String res= "success";
	 return res;
	}
	
	
}

