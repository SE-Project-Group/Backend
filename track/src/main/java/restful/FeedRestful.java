package restful;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import model.Client;
import model.Comment;
import model.Feed;
import model.ReturnComment;
import model.ReturnFeed;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.ClientService;
import service.FeedService;
import service.FollowService;
import service.JPushService;
import util.SpringContextUtil;

@Path("/app/feed")
public class FeedRestful {
	
	private JPushService jpushService=(JPushService) SpringContextUtil.getBean("jpushService");
	private FeedService feedService=(FeedService) SpringContextUtil.getBean("feedService");
	private ClientService clientService=(ClientService) SpringContextUtil.getBean("clientService");
	private FollowService followService=(FollowService) SpringContextUtil.getBean("followService");
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
			 @QueryParam("user_id") int userId) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedInfo,Feed.class);
		 feedService.newFeed(feed);
		 Collection<String> alias=jpushService.getFollowerIdById(userId);
		 Map<String,String>resmap=new HashMap<String,String>();
			
	
		
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String time=df.format(new Date(System.currentTimeMillis()));
	     resmap.put("time", time);

		 Gson json=new Gson(); 
		 
		 
		 String msgContent=json.toJson(resmap);
		 msgContent="NewFollowFeedMessage#"+ msgContent;
		 jpushService.sendMessageByAlias(alias, msgContent);
		 
		 List<Integer>mention=feed.getMentionList();
		 List<String> smention = new ArrayList<String>(mention.size()) ;
				 for (Integer myInt : mention) { 
					 smention.add(String.valueOf(myInt)); 
				 }
		Map<String,String>mresmap=new HashMap<String,String>();

		resmap.put("user_id",String.valueOf( userId));
		
		Client user=clientService.getClientById(userId);
		 String user_name=user.getUserName();
		 resmap.put("user_name",user_name);
		 
		/* String portrait_url=clientService.getPortraitUrl(userId);
		 resmap.put("portrait_url", portrait_url);*/
		
	     resmap.put("time", time);
		
	     String _id=feed.get_id();
	     resmap.put("feed_id",_id);
	     
          Gson mjson=new Gson(); 
		 
		 
		 String amsgContent=mjson.toJson(mresmap);
		 amsgContent="NewMentionMessage#"+amsgContent;
		 jpushService.sendMessageByAlias(smention, amsgContent);
		 return _id;
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
	@PUT
    @Path("/updateFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String updateFeed(String feedinfo) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		 Gson gson=new Gson();
		 Feed feed=gson.fromJson(feedinfo,Feed.class);	 
		 feedService.updateFeed(feed);
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
	@DELETE
    @Path("/removeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String removeFeed(String feedInfo) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject newfeed = JSONObject.fromObject(feedInfo);
		String _id= newfeed.getString("_id");
		feedService.removeFeed(_id);
		return "success";
     }	
	/**
	 * 获取自己的所有feed
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
			@QueryParam("user_id") int userId )throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		 List<Feed> feeds=feedService.findFeedByUserId(userId);
		 List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		 return JSONArray.fromObject(res).toString();
	}
	/**
	 * 未登录状态下获取某人所有公开动态
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
		 List<Feed> feeds=feedService.findPublicFeedsById(who);
		 List<ReturnFeed>res=feedService.feedToReturnFeed(feeds,0);
		 return JSONArray.fromObject(res).toString();
	}	
	/**
	 * 登录状态下获取某人动态
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
			@QueryParam("who") int who)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		List<Feed> feeds=feedService.getFeedsLoggedIn(userId,who);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		return JSONArray.fromObject(res).toString();	 
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
	@Path("getPublicFeedAfterTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getPublicFeedAfterTime(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=feedService.findPublicFeedsAfterTime(ts,userId);
		return JSONArray.fromObject(list).toString();
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
	@Path("getPublicFeedBeforeTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String getPublicFeedBeforeTime(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<ReturnFeed> list=feedService.findPublicFeedsBeforeTime(ts,userId);
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
			@QueryParam("user_id") int userId) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		List<ReturnFeed>feeds=feedService.findFeedAround(longitude, latitude, 10,userId);
		return JSONArray.fromObject(feeds).toString();
	}
	@GET
	@Path("/compareFeedSameLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public String compareFeedSameLocation(
			@QueryParam("longitude") double longitude,
			@QueryParam("latitude") double latitude,
			@QueryParam("user_id") int userId) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		List<ReturnFeed>feeds=feedService.findFeedAroundSpecUser(longitude, latitude, 10,userId);
		return JSONArray.fromObject(feeds).toString();
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
	 @PUT
     @Path("/incLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String incLikeFeed(String feedInfo){
		 JSONObject newfeed = JSONObject.fromObject(feedInfo);
		 String _id= newfeed.getString("_id");
		 int user_id=newfeed.getInt("user_id");
		 Feed feed=feedService.getFeed(_id);
		 String shareId=feed.getShareId();
		 if(!shareId.equals("")){
			 _id=shareId;
		 }
		 int iowner=feedService.incLikeFeed(_id,user_id);
		 String owner=String.valueOf(iowner);
		 Map<String,String>resmap=new HashMap<String,String>();
		
		 
		 resmap.put("user_id",String.valueOf( user_id));
		
		 Client user=clientService.getClientById(user_id);
		 String user_name=user.getUserName();
		 resmap.put("user_name",user_name);
		 
		/* String portrait_url=clientService.getPortraitUrl(user_id);
		 resmap.put("portrait_url", portrait_url);*/
		
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String time=df.format(new Date(System.currentTimeMillis()));
	     resmap.put("time", time);
		
/*	     String relationship=followService.getRelationship(iowner, user_id);
	     resmap.put("relationship",relationship);*/
	     resmap.put("feed_id",_id);
	     resmap.put("status", "");
	     

		 Gson json=new Gson(); 
		 
		 
		 String msgContent=json.toJson(resmap);
		 msgContent="NewLikeMessage#"+msgContent;
         jpushService.sendMessageByAlias(owner,msgContent);    
		 return "success";
     }
	 
	 /**
	  * 取消赞
	  * @param feedInfo
	  * @param userId
	  * @param sign
	  * @return
	  * @throws JSONException
	  * @throws NoSuchAlgorithmException
	  * @throws UnsupportedEncodingException
	  */
	 
	 @PUT
     @Path("/decLikeFeed")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	 public String decLikeFeed(String feedInfo){
		 JSONObject newfeed = JSONObject.fromObject(feedInfo);
		 String _id= newfeed.getString("_id");
		 int user_id=newfeed.getInt("user_id");
		 feedService.decLikeFeed(_id,user_id);  
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
	@PUT
	@Path("/newComment")
	@Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
	public String newComment(String commentInfo)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject newfeed=JSONObject.fromObject(commentInfo);
		String _id= newfeed.getString("_id");
		int user_id=newfeed.getInt("user_id");
		String text=newfeed.getString("text");
		int replyId=newfeed.getInt("reply_id");
		int iowner=feedService.newComment( _id, user_id, text,  replyId);
		 String owner=String.valueOf(iowner);
		 Map<String,String>resmap=new HashMap<String,String>();
			
		 
		 resmap.put("user_id",String.valueOf( user_id));
		
		 Client user=clientService.getClientById(user_id);
		 String user_name=user.getUserName();
		 resmap.put("user_name",user_name);
		 
		/* String portrait_url=clientService.getPortraitUrl(user_id);
		 resmap.put("portrait_url", portrait_url);*/
		
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String time=df.format(new Date(System.currentTimeMillis()));
	     resmap.put("time", time);
		
	     resmap.put("comment_text",text);
	     
	     String relationship=followService.getRelationship(iowner, user_id);
	     resmap.put("relationship",relationship);
	     resmap.put("feed_id",_id);
	     
	     resmap.put("reply_id",String.valueOf(replyId));
	     resmap.put("status", "");
		 Gson json=new Gson(); 
		 
		 
		 String msgContent=json.toJson(resmap);
		 msgContent="NewCommentMessage#"+msgContent;
	     jpushService.sendMessageByAlias(owner, msgContent);
		 return "success";
	}
	/**
	 * 获取某动态的评论列表
	 * @param feedId
	 * @return
	 * @throws JSONException
	 */
	
	@GET
	@Path("/commentList")
	@Produces(MediaType.APPLICATION_JSON)
	public String commentList(
			 @QueryParam("feed_id") String feedId)throws JSONException{
		List<Comment>comments=feedService.findCommentList(feedId);
		List<ReturnComment>res=feedService.commentToReturnComment(comments);
		return JSONArray.fromObject(res).toString();
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
	@GET
	@Path("getFriendFeedList")
	@Produces("text/html")
	public String getFriendFeedList(
			@QueryParam("time") String time,
			@QueryParam("user_id") int userId){ 
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=feedService.getFriendFeedList(ts,userId);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		return JSONArray.fromObject(res).toString();
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
	@Produces("text/html")
	public String getAllFeedList(
			@QueryParam("user_id") int userId,
			@QueryParam("time") String time){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);	
		List<Feed> feeds=feedService.getAllFeedList(ts);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		return JSONArray.fromObject(res).toString();
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
	@Produces("text/html")
	public String getFollowingFeedList(
			@QueryParam("user_id") int userId,
			@QueryParam("time") String time){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);
		List<Feed> feeds=feedService.getFollowingFeedList(ts,userId);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		/*List<ReturnShareFeed> res2=feedService.getFollowingShareFeedList(ts, userId);*/
		return JSONArray.fromObject(res).toString()/*+JSONArray.fromObject(res2).toString()*/;
	}
	
	@GET
	@Path("searchFeed")
	@Produces("text/html")
	public String searchFeed(@QueryParam("query") String query){
		List<Feed> feeds=feedService.searchFeed(query);
		
		return JSONArray.fromObject(feeds).toString();
		
	}
	
	@POST
	@Path("shareFeed")
	@Produces("text/html")
	public String shareFeed(
			String info,
			@QueryParam("user_id") int userId){
		JSONObject obj=JSONObject.fromObject(info);
		String feedId=obj.getString("feed_id");
		String text=obj.getString("text");
		if(feedService.shareFeed(userId,feedId,text)){
			return "success";
		}
		return "failed";
	}
}
