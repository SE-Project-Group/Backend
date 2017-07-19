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
	 * �û���¼
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
	 * �û��ǳ�
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
		appService.logout(userId);
		return "success";
	}
	
/**
 * �û�ע��
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
	 * ��ѯ�û�������Ϣ
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
		if(sign==null)return null;
		Client client=appService.getClientById(userId);
		if(client==null)return null;
		String shortFormat = "yyyy-MM-dd";  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 
		JSONObject json = JSONUtil.toJson(client,processors);
		return json.toString();
	}
	/**
	 * �޸��û�������Ϣ
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
		if(sign==null)return null;
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
	 * �����¶�̬
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
	 * ���¶�̬
	 * @param feedinfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@PUT
    @Path("/updateFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String updateFeed(String feedinfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign ) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedinfo,Feed.class);	 
		 appService.updateFeed(feed);
		 return "success";
     }

	
	/**
	 * ɾ����̬
	 * @param feedInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@DELETE
    @Path("/removeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String removeFeed(String feedInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign ) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject newfeed = JSONObject.fromObject(feedInfo);
		String _id= newfeed.getString("_id");
		appService.removeFeed(_id);
		return "success";
     }
	
	/**
	 * ��ȡ�Լ�������feed
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
		 List<Feed> feeds=appService.findFeedByUserId(userId);
		 List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		 return JSONArray.fromObject(res).toString();
	}
	
	/**
	 * δ��¼״̬�»�ȡĳ�����й�����̬
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	
	@GET
	@Path("/getFeedsNotLoggedIn")
	@Produces("text/html")
	public  String getFeedsNotLoggedIn(
			@QueryParam("who") int who)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		 List<Feed> feeds=appService.findPublicFeedsById(who);
		 List<ReturnFeed>res=appService.feedToReturnFeed(feeds);
		 return JSONArray.fromObject(res).toString();
	}
	
	/**
	 * ��¼״̬�»�ȡĳ�˶�̬
	 * @param userId
	 * @param sign
	 * @param who
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	
	@GET
	@Path("/getFeedsLoggedIn")
	@Produces("text/html")
	public  String getFeedsLoggedIn(
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("who") int who)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		List<Feed> feeds=appService.getFeedsLoggedIn(userId,who);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();	 
	}
	
	/**
	 * ��ȡtime֮�������public��feed
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
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=appService.findPublicFeedsByTime(ts);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * ��ȡ�ܱߵĶ�̬
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
		List<ReturnFeed>feeds=appService.findFeedAround(longitude, latitude, 10);
		return JSONArray.fromObject(feeds).toString();
	}

	/**
	 * Ϊ��̬����
	 * @param feedInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	 @PUT
     @Path("/incLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String incLikeFeed(String feedInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 JSONObject newfeed = JSONObject.fromObject(feedInfo);
		 String _id= newfeed.getString("_id");
		 int user_id=newfeed.getInt("user_id");
		 String owner=String.valueOf(appService.incLikeFeed(_id,user_id));
		 String msgContent="NewLike";
		/* long s = System.currentTimeMillis();
	     List<String>name=new ArrayList<String>();
	     long e = System.currentTimeMillis(); 
	     for(int i=0;i<100000;i++)
	     {name.add("zhao");}
	     long start = System.currentTimeMillis();*/
		// Map<String,String>uri=new HashMap<String,String>();
		// uri.put("uri", "http://test");
         jpushService.senMessageByAlias(owner,msgContent);
        /* long end = System.currentTimeMillis();       // ��¼����ʱ��
         System.out.println(end-start); */      
		 return "success";
     }

	/**
	 * Ϊ��̬�������
	 * @param commentInfo
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@PUT
	@Path("/newComment")
	@Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	public String newComment(String commentInfo,
			 @QueryParam("user_id") int userId,
			 @QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
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
	 * ��ȡ���ѵĶ�̬�б�
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Path("getFriendFeedList")
	 @Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getFriendFeedList(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFriendFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=appService.getFriendFeedList(ts,userId);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}
	/**
	 * ��ȡȫ���Ķ�̬�б�
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
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("time") String time)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getAllFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=appService.getAllFeedList(ts);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}
    /**
     * ��ȡ�ҹ�ע���˵Ķ�̬���б�
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
	public String getFollowingFeedList(
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign,
			@QueryParam("time") String time)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingFeedList", sign))return "status wrong"; 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);
		List<Feed> feeds=appService.getFollowingFeedList(ts,userId);
		List<ReturnFeed> res=appService.feedToReturnFeed(feeds);
		return JSONArray.fromObject(res).toString();
	}
	/**
	 * ��ȡ�ҵ����ѵ���Ϣ
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
			@QueryParam("user_id") int userId,
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
	 * ��ȡ�ҹ�ע���˵���Ϣ
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
	public String getFollowingInformationById(@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getFollowingInformationById", sign))return "status wrong"; 
		
		List<ReturnFollow> list=appService.getFollowingInformationById(userId);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	/**
	 * ��ȡ��ע�ҵ��˵���Ϣ
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
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		/*if(!appService.checkSign(userId, "track/rest/app/getFollowerInformationById", sign))return "status wrong"; */
	
		List<ReturnFollow> list=appService.getFollowerInformationById(userId);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	/**
	 * ��ȡ�����û�����Ϣ
	 * @param userId
	 * @param sign
	 * @param id
	 * @return
	 */
	@GET
	@Path("getInfo")
	@Produces("text/html")
	public String getInfo(
			@QueryParam("user_id") int userId){
		/*if(!appService.checkSign(userId, "track/rest/app/getInfo", sign))return "status wrong"; */
		ReturnUserInfo rui=appService.getSomeoneInfo(userId);
		JSONObject obj=JSONObject.fromObject(rui);
		return obj.toString();
	}
	/**
	 * ��עĳ��
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
			@QueryParam("user_id") int userId,
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
	 * ȡ������ĳ�˵Ĺ�ע
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
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/deleteFollow", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));	
		String res=appService.unFollowSomeone(userId,followId);
		return res;
	}

	/**
	 * ������ҳ
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
			@QueryParam("user_id") int userId,
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

