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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;

import edu.emory.mathcs.backport.java.util.Collections;
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
	 * 閸欐垵绔烽弬鏉垮З閹拷
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
	     resmap.put("text", feed.getText());
	     
          Gson mjson=new Gson(); 
		 
		 
		 String amsgContent=mjson.toJson(mresmap);
		 amsgContent="NewMentionMessage#"+amsgContent;
		 jpushService.sendMessageByAlias(smention, amsgContent);
		 return _id;
     }

	/**
	 * 閼惧嘲褰囬懛顏勭箒閻ㄥ嫭澧嶉張濉甧ed
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
		 Collections.reverse(res);
		 return JSONArray.fromObject(res).toString();
	}
	/**
	 * 閺堫亞娅ヨぐ鏇犲Ц閹椒绗呴懢宄板絿閺屾劒姹夐幍锟介張澶婂彆瀵拷閸斻劍锟斤拷
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
	 * 閻ц缍嶉悩鑸碉拷浣风瑓閼惧嘲褰囬弻鎰眽閸斻劍锟斤拷
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
	 * 閼惧嘲褰噒ime娑斿鎮楅惃鍕閺堝「ublic閻ㄥ垿eed
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
	 * 閼惧嘲褰噒ime娑斿鎮楅惃鍕閺堝「ublic閻ㄥ垿eed
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
	 * 閼惧嘲褰囬崨銊ㄧ珶閻ㄥ嫬濮╅幀锟�
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
	 * 娑撳搫濮╅幀浣哄仯鐠э拷
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
	  * 閸欐牗绉风挧锟�
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
	 * 娑撳搫濮╅幀浣瑰潑閸旂姾鐦庣拋锟�
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
		String  ownercommentid=feedService.newComment( _id, user_id, text,  replyId);
		//ocr[0]涓簅wnerID锛宱cr[1]涓篶omment_id锛宱cr[2]涓簉eply_id
		String ocr[]=ownercommentid.split(",");
		int iowner=Integer.valueOf(ocr[0]);
		//int comment_id=Integer.valueOf(oc[1]);
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
	     
	     resmap.put("comment_id",ocr[1]);
	     resmap.put("status", "");
		 Gson json=new Gson(); 
		 
		 
		 String msgContent=json.toJson(resmap);
		 msgContent="NewCommentMessage#"+msgContent;
		 if(user_id==iowner){}
		 else
		 if(replyId==0){jpushService.sendMessageByAlias(owner, msgContent);}
		 else{
			 jpushService.sendMessageByAlias(ocr[2], msgContent);
		 }
		 return "success";
	}
	/**
	 * 閼惧嘲褰囬弻鎰З閹胶娈戠拠鍕啈閸掓銆�
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
	 * 閼惧嘲褰囨總钘夊几閻ㄥ嫬濮╅幀浣稿灙鐞涳拷
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
	 * 閼惧嘲褰囬崗銊╁劥閻ㄥ嫬濮╅幀浣稿灙鐞涳拷
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
     * 閼惧嘲褰囬幋鎴濆彠濞夈劎娈戞禍铏规畱閸斻劍锟戒胶娈戦崚妤勩��
     * @param tstring
     * @param userId
     * @param sign
     * @return
     * @throws JSONException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
	@GET

	@Path("getFollowingFeedsBeforeTime")
	@Produces("text/html")
	public String getFollowingFeedsBeforeTime(
			@QueryParam("user_id") int userId,
			@QueryParam("time") String time){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);

		List<Feed> feeds=feedService.getFollowingFeedsBeforeTime(ts,userId);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("getFollowingFeedsAfterTime")
	@Produces("text/html")
	public String getFollowingFeedsAfterTime(
			@QueryParam("user_id") int userId,
			@QueryParam("time") String time){
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		ts=Timestamp.valueOf(time);
		List<Feed> feeds=feedService.getFollowingFeedsAfterTime(ts,userId);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds,userId);
		return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("searchFeed")
	@Produces("text/html")
	public String searchFeed(
			@QueryParam("query") String query,
			@QueryParam("user_id") int user_id){
		List<Feed> feeds=feedService.searchFeed(query);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds, user_id);
		
		return JSONArray.fromObject(res).toString();
		
	}
	
	@PUT
	@Path("shareFeed")
	@Produces("text/html")
	public String shareFeed(
			String info,
			@QueryParam("user_id") int userId){
		JSONObject obj=JSONObject.fromObject(info);
		String feedId=obj.getString("feed_id");
		String text=obj.getString("share_text");
		int owner = obj.getInt("owner");
		Map<String,String>resmap=new HashMap<String,String>();
		if(userId!=owner){
			resmap.put("user_id",String.valueOf(userId));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String time=df.format(new Date(System.currentTimeMillis()));
		    resmap.put("time", time);
		    resmap.put("user_name", clientService.getClientById(userId).getUserName());
		    resmap.put("feed_id", feedId);
		    resmap.put("share_comment", text);
			Gson json=new Gson(); 	 
			String msgContent=json.toJson(resmap);
			msgContent="NewShareMessage#"+msgContent;
			jpushService.sendMessageByAlias(String.valueOf( owner), msgContent);
		}
		return feedService.shareFeed(userId,feedId,text);
	}
	
	@GET
	@Path("getOriginPhotoUrl")
	@Produces("text/html")
	public String getOriginPhotoUrl(@QueryParam("fileName") String fileName){
		return feedService.getOriginPhotoUrl(fileName);
	}
	
	@GET
	@Path("getBigPhotoUrls")
	@Produces("text/html")
	public String getBigPhotoUrls(@QueryParam("feed_id") String feedId){
		return feedService.getBigPhotoUrls(feedId);
	}
		
	@GET
	@Path("myLikeFeeds")
	@Produces("text/html")
	public String myLikeFeeds(
			@QueryParam("user_id") int userId){
		
		List<ReturnFeed> feeds=feedService.myLikeFeeds(userId);
		
		/*List<ReturnShareFeed> res2=feedService.getFollowingShareFeedList(ts, userId);*/
		return JSONArray.fromObject(feeds).toString()/*+JSONArray.fromObject(res2).toString()*/;
	}
	
	@GET
	@Path("myCommentFeeds")
	@Produces("text/html")
	public String myCommentFeeds(
			@QueryParam("user_id") int userId){
		
		List<ReturnFeed> feeds=feedService.myCommentFeeds(userId);
		
		/*List<ReturnShareFeed> res2=feedService.getFollowingShareFeedList(ts, userId);*/
		return JSONArray.fromObject(feeds).toString()/*+JSONArray.fromObject(res2).toString()*/;
	}
	
	@GET
	@Path("myShareFeeds")
	@Produces("text/html")
	public String myShareFeeds(
			@QueryParam("user_id") int userId){	
		List<ReturnFeed> res=feedService.myShareFeeds(userId);
		return JSONArray.fromObject(res).toString();
	}
	
	@GET
	@Path("getFeedDetail")
	@Produces("text/html")
	public String getFeedDetail(
			@QueryParam("user_id") int userId,
			@QueryParam("feed_id") String feedId){	
		Feed feed=feedService.getFeed(feedId);
		if(feed==null){
			return "failed";
		}
		List<Feed> feeds=new ArrayList<Feed>();
		feeds.add(feed);
		List<ReturnFeed> res=feedService.feedToReturnFeed(feeds, userId);
		return JSONObject.fromObject(res.get(0)).toString();
	}
}
