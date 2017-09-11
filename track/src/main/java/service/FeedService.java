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
	 * ����feed
	 * @param feedId
	 * @return
	 */
	
	public Feed getFeed(String feedId);
	
	/**
	 * �½�feed
	 * @param feed
	 */
	public void newFeed(Feed feed);
	
	/**
	 * �ҳ�idΪuserid���û������ж�̬
	 * @param userId
	 * @return
	 */
	public List<Feed>findFeedByUserId(int userId);
	
	/**
	 * ��ȡĳ������public��̬
	 * @param userId
	 * @return
	 */
	public List<Feed>findPublicFeedsById(int userId);
	/**
	 * �ҳ��뾶Ϊradius������Ϊlongitude��latitude�ķ�Χ�ڵĶ�̬
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return
	 */
	public List<ReturnFeed>findFeedAround(double longitude,double latitude,double radius,int userId);
	/**
	 * �ҳ��뾶Ϊradius������Ϊlongitude��latitude�ķ�Χ�ڸ��û��Ķ�̬
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param userId
	 * @return
	 */
	public List<ReturnFeed>findFeedAroundSpecUser(double longitude,double latitude,double radius,int userId);
	/**
	 * �ҳ�time֮������е�public��feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsAfterTime(Timestamp time,int userId);
	/**
	 * �ҳ�time֮ǰ�����е�public��feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsBeforeTime(Timestamp time,int userId);
	
	/**
	 * ��¼״̬�»�ȡ��̬
	 * @param userId
	 * @param who
	 * @return
	 */
	public List<Feed> getFeedsLoggedIn(int userId,int who);
	
	
	/**
	 * �ҳ�time֮���idΪuserid���˵�����friend�Ķ�̬
	 * @param time
	 * @param userid
	 * @return
	 */
	public List<Feed> getFriendFeedList(Timestamp time,int userid);
	
	/**
	 * �ҳ�time֮������е�feed
	 * @param time
	 * @return
	 */
	public List<Feed> getAllFeedList(Timestamp time);
	
	/**
	 * �ҳ�time֮�������idΪuserid���˹�ע���˵Ķ�̬
	 * @param time
	 * @param userid
	 * @return
	 */
	public List<Feed> getFollowingFeedsAfterTime(Timestamp time,int userid);

	
	/**
	 * �ҳ�time֮ǰ������idΪuserid���˹�ע���˵Ķ�̬
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
	 * ΪidΪ_id��feed������������¼�µ����˵�userid�͵���ʱ��
	 * @param _id
	 * @param userId
	 * @return�������˵�userId
	 */
	 
	public int incLikeFeed(String _id,int userId);
	
	/**
	 * ȡ����
	 * @param _id
	 * @param userId
	 */
	public void decLikeFeed(String _id,int userId);
	/*comment*/
	/**
	 * ΪidΪ_id��feed�������ۣ�����Ϊtext��������Ϊuserid���Զ���������id����¼����ʱ���Լ��ظ���˭
	 * @param _id
	 * @param userId
	 * @param text
	 * @param replyIdΪ0Ϊ�ظ������ۣ���Ϊ0��Ϊ�ظ�idΪreplyId������
	 * @return �������˵�userId
	 */
	public String newComment(String _id,int userId,String text,int replyId);
	/**
	 * ����ָ����̬�������б�
	 * @param feedId
	 * @return
	 */
	public List<Comment> findCommentList(String feedId);
	
	/**
	 * ת��
	 * @param userId
	 * @param feedId
	 */
	public String shareFeed(int userId, String feedId,String text);
	/**
	 * 
	 * transfer
	 * 
	 */
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds,int userid);
	public List<ReturnComment> commentToReturnComment(List<Comment> comments);

	public List<Feed> searchFeed(String query);
	
	public String getOriginPhotoUrl(String fileName);
	
	public String getBigPhotoUrls(String feedId);
	
	public List<ReturnFeed> myLikeFeeds(int userid);
	public List<ReturnFeed> myCommentFeeds(int userid);
	public List<ReturnFeed> myShareFeeds(int userId);
	
	
	public List<Feed> sortFeed(List<Feed> feeds);
}
