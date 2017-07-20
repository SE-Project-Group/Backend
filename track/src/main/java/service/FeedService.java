package service;

import java.sql.Timestamp;
import java.util.List;

import model.Feed;
import model.ReturnFeed;

public interface FeedService {

	/*
	 * 
	 * Feed
	 */
	/**
	 * �½�feed
	 * @param feed
	 */
	public void newFeed(Feed feed);
	
	/**
	 * ����feed
	 * @param feed
	 */
	public void updateFeed(Feed feed);
	
	/**
	 * ɾ��feed
	 * @param _id
	 */
	public void removeFeed(String _id);
	
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
	public List<ReturnFeed>findFeedAround(double longitude,double latitude,double radius);
	/**
	 * �ҳ�time֮������е�public��feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsAfterTime(Timestamp time);
	/**
	 * �ҳ�time֮ǰ�����е�public��feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsBeforeTime(Timestamp time);
	
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
	public List<Feed> getFollowingFeedList(Timestamp time,int userid);
	/*like*/
	/**
	 * ΪidΪ_id��feed������������¼�µ����˵�userid�͵���ʱ��
	 * @param _id
	 * @param userId
	 * @return�������˵�userId
	 */
	 
	public int incLikeFeed(String _id,int userId);
	/*comment*/
	/**
	 * ΪidΪ_id��feed�������ۣ�����Ϊtext��������Ϊuserid���Զ���������id����¼����ʱ���Լ��ظ���˭
	 * @param _id
	 * @param userId
	 * @param text
	 * @param replyIdΪ0Ϊ�ظ������ۣ���Ϊ0��Ϊ�ظ�idΪreplyId������
	 * @return �������˵�userId
	 */
	public int newComment(String _id,int userId,String text,int replyId);
	
	/**
	 * 
	 * transfer
	 * 
	 */
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds);
}