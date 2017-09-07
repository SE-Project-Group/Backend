package service;

import java.sql.Timestamp;
import java.util.List;

import model.Comment;
import model.Feed;
import model.ReturnComment;
import model.ReturnFeed;

public interface FeedService {

	/*
	 * 
	 * Feed
	 */
	
	/**
	 * 查找feed
	 * @param feedId
	 * @return
	 */
	
	public Feed getFeed(String feedId);
	
	/**
	 * 新建feed
	 * @param feed
	 */
	public void newFeed(Feed feed);
	
	/**
	 * 更新feed
	 * @param feed
	 */
	public void updateFeed(Feed feed);
	
	/**
	 * 删除feed
	 * @param _id
	 */
	public void removeFeed(String _id);
	
	/**
	 * 找出id为userid的用户的所有动态
	 * @param userId
	 * @return
	 */
	public List<Feed>findFeedByUserId(int userId);
	
	/**
	 * 获取某人所有public动态
	 * @param userId
	 * @return
	 */
	public List<Feed>findPublicFeedsById(int userId);
	/**
	 * 找出半径为radius，中心为longitude，latitude的范围内的动态
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return
	 */
	public List<ReturnFeed>findFeedAround(double longitude,double latitude,double radius,int userId);
	/**
	 * 找出半径为radius，中心为longitude，latitude的范围内该用户的动态
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param userId
	 * @return
	 */
	public List<ReturnFeed>findFeedAroundSpecUser(double longitude,double latitude,double radius,int userId);
	/**
	 * 找出time之后的所有的public的feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsAfterTime(Timestamp time,int userId);
	/**
	 * 找出time之前的所有的public的feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsBeforeTime(Timestamp time,int userId);
	
	/**
	 * 登录状态下获取动态
	 * @param userId
	 * @param who
	 * @return
	 */
	public List<Feed> getFeedsLoggedIn(int userId,int who);
	
	
	/**
	 * 找出time之后的id为userid的人的所有friend的动态
	 * @param time
	 * @param userid
	 * @return
	 */
	public List<Feed> getFriendFeedList(Timestamp time,int userid);
	
	/**
	 * 找出time之后的所有的feed
	 * @param time
	 * @return
	 */
	public List<Feed> getAllFeedList(Timestamp time);
	
	/**
	 * 找出time之后的所有id为userid的人关注的人的动态
	 * @param time
	 * @param userid
	 * @return
	 */
	public List<Feed> getFollowingFeedsAfterTime(Timestamp time,int userid);
	
	/**
	 * 找出time之前的所有id为userid的人关注的人的动态
	 * @param time
	 * @param userid
	 * @return
	 */
	public List<Feed> getFollowingFeedsBeforeTime(Timestamp time,int userid);
	
	
	/**
	 * 
	 * 
	 * @param time
	 * @param userId
	 * @return
	 */
	
/*	public List<ReturnShareFeed> getFollowingShareFeedList(Timestamp time,int userId);*/
	
	/*like*/
	/**
	 * 为id为_id的feed增加赞数并记录下点赞人的userid和点赞时间
	 * @param _id
	 * @param userId
	 * @return被点赞人的userId
	 */
	 
	public int incLikeFeed(String _id,int userId);
	
	/**
	 * 取消赞
	 * @param _id
	 * @param userId
	 */
	public void decLikeFeed(String _id,int userId);
	/*comment*/
	/**
	 * 为id为_id的feed加入评论，内容为text，评论人为userid，自动生成评论id，记录评论时间以及回复的谁
	 * @param _id
	 * @param userId
	 * @param text
	 * @param replyId为0为回复的评论，不为0则为回复id为replyId的评论
	 * @return 被评论人的userId
	 */
	public String newComment(String _id,int userId,String text,int replyId);
	/**
	 * 查找指定动态的评论列表
	 * @param feedId
	 * @return
	 */
	public List<Comment> findCommentList(String feedId);
	
	/**
	 * 转发
	 * @param userId
	 * @param feedId
	 */
	public boolean shareFeed(int userId, String feedId,String text);
	/**
	 * 
	 * transfer
	 * 
	 */
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds,int userid);
	public List<ReturnComment> commentToReturnComment(List<Comment> comments);

	public List<Feed> searchFeed(String query);
	
	public String getOriginPhoto(String fileName);
	
	public String getBigPhotoUrls(String feedId);
	
	public List<ReturnFeed> myLikeFeeds(int userid);
	public List<ReturnFeed> myCommentFeeds(int userid);
	public List<ReturnFeed> myShareFeeds(int userId);
	
	
	public List<Feed> sortFeed(List<Feed> feeds);
}
