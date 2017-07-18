package restful;

import model.Client;
import model.Feed;
import model.ReturnFeed;
import model.ReturnFollow;
import model.ReturnUserInfo;
import model.SignedUrlFactory;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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
import service.JPushService;
import util.JSONUtil;
import util.SQLDateProcessor;
import util.SpringContextUtil;



@Path("/app")
public class Restful {
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
	private JPushService jpushService=(JPushService) SpringContextUtil.getBean("jpushService");
	
	/**
	 * 用户登录
	 * @URL http://192.168.1.13:8088/track/rest/app/clientLogin?user_name=**&password=** 
	 * @param userName
	 * @param password
	 * @return JSON
	 * {
     *"token": "1771fcf8a5c149e48da138099fe2ff53",
     *"userId": 2
     * }
	 */
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


	/**
	 * 用户登出
	 * @param userId
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	
/**
 * 用户注册
 * @param message
 * @return
 * @throws JSONException
 */
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
	/**
	 * 查询用户个人信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	/**
	 * 修改用户个人信息
	 * @param message
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	/**
	 * 发布新动态
	 * @param feedInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		 Collection<String> alias=jpushService.getFollowerIdById(userId);
		 String msgContent="Your Friend Has new msg";
		 jpushService.senMessageByAlias(alias, msgContent);
		 String res= "success";
		 return res;
     }
	/**
	 * 更新动态
	 * @param feedinfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	/**
	 * 删除动态
	 * @param feedInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	
	/**
	 * 获取id为userid的feed
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	
	/**
	 * 获取time之后的所有public的feed
	 * @param time
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		List<Feed> list=appService.findPublicFeedsByTime(ts);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * 获取周边的动态
	 * @param longitude
	 * @param latitude
	 * @param userId
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		SignedUrlFactory factory = new SignedUrlFactory();
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
			returnFeed.setPicUrls(factory.getPicUrls(curFeed.get_id(), curFeed.getPicCount()));
			res.add(returnFeed);
		}
		return JSONArray.fromObject(res).toString();
	}
	/**
	 * 为动态点赞
	 * @param feedInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		 String owner=String.valueOf(appService.incLikeFeed(_id,user_id));
		String msgContent="NewLike";
          jpushService.senMessageByAlias(owner, msgContent);
		 return "success";
     }
	/**
	 * 为动态添加评论
	 * @param commentInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		
		 String owner=String.valueOf(appService.newComment( _id, id, text,  replyId));
			String msgContent="NewLike";
	          jpushService.senMessageByAlias(owner, msgContent);
			 return "success";
	}
	/**
	 * 获取好友的动态列表
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("getFriendFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFriendFeedList(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFriendFeedList", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		String time=tsinfo.getString("time");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> list=appService.getFriendFeedList(ts,userId);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * 获取全部的动态列表
	 * @param userId
	 * @param sign
	 * @param time
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		List<Feed> list=appService.getAllFeedList(ts);
		return JSONArray.fromObject(list).toString();
	}
    /**
     * 获取我关注的人的动态的列表
     * @param tstring
     * @param userId
     * @param sign
     * @return
     * @throws JSONException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	@GET
	@Path("getFollowingFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFollowingFeedList(String tstring,
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingFeedList", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		String time=tsinfo.getString("time");
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);
		List<Feed> list=appService.getFollowingFeedList(ts,userId);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * 获取我的朋友的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
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
	/**
	 * 获取我关注的人的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
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
	/**
	 * 获取关注我的人的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
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
	/**
	 * 获取单个用户的信息
	 * @param userId
	 * @param sign
	 * @param id
	 * @return
	 */
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
	/**
	 * 关注某人
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
		String msgContent="NewFollower";
		String sfollowId=String.valueOf(followId);
        jpushService.senMessageByAlias(sfollowId, msgContent);
		return res;
	}
	/**
	 * 取消对于某人的关注
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
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
	/**
	 * 个人主页
	 * @param userId
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Path("homepage")
	@Produces(MediaType.APPLICATION_JSON)
	public String homepage(
			@QueryParam("user_ID") int userId,
			@QueryParam("sign") String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		/*if(!appService.checkSign(userId, "track/rest/app/homepage", sign))return "status wrong"; */
		Client client=appService.getClientById(userId);
		String phone=client.getPhone();
		String email=client.getEmail();
		int followed=0;
		int following=0;
		if(appService.getFollowerInformationById(userId)!=null){
			followed=appService.getFollowerInformationById(userId).size();
		}
		if(appService.getFollowingInformationById(userId)!=null){
			following=appService.getFollowingInformationById(userId).size();
		}
		JSONObject obj=new JSONObject();
		obj.put("phone", phone);
		obj.put("email", email);
		obj.put("following", following);
		obj.put("followed", followed);
		return obj.toString();
	}
}

