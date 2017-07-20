package restful;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
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

import model.Feed;
import model.ReturnFeed;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.AppService;
import service.JPushService;
import util.SpringContextUtil;

@Path("/app/feed")
public class FeedRestful {
	private AppService appService=(AppService) SpringContextUtil.getBean("appService");
	private JPushService jpushService=(JPushService) SpringContextUtil.getBean("jpushService");
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
		 List<Integer>mention=feed.getMentionList();
		 List<String> smention = new ArrayList<String>(mention.size()) ;
				 for (Integer myInt : mention) { 
					 smention.add(String.valueOf(myInt)); 
				 }
		Map<String,String>extra=new HashMap<String,String>();
		extra.put("_id", feed.get_id());
			jpushService.senPushByAlias(smention,"Your friend mentions you!",feed.getText(),extra);	 
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
	@Path("getPublicFeedAfterTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getPublicFeedAfterTime(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=appService.findPublicFeedsAfterTime(ts);
		return JSONArray.fromObject(list).toString();
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
	@Path("getPublicFeedBeforeTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getPublicFeedBeforeTime(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId,
			@QueryParam("sign") String sign)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=appService.findPublicFeedsBeforeTime(ts);
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
	 * Ϊ��̬��������
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
}