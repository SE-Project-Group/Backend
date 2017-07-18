package service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.springframework.data.geo.Point;

import model.Client;
import model.Feed;
import model.Follow;
import model.Location;
import model.ReturnFeed;
import model.ReturnFollow;
import model.ReturnUserInfo;
import model.Token;

public interface AppService {
	
	/*
	 * 
	 * Client
	 */
	
	/**
	 * �����û�
	 * @param client
	 */
	public void insertClient(Client client);
	
	/**
	 * �����û�
	 * @param client
	 */
	public void updateClient(Client client);
	
	/**
	 * ɾ���û�
	 * @param client
	 */
	public void deleteClient(Client client);
	
	/**
	 * ͨ��id��ȡ�û�
	 * @param id
	 * @return
	 */
	public Client getClientById(int id);
	
	/**
	 * ��ȡȫ���û�
	 * @return
	 */
	public List<Client> getAllClients();
	
	/**
	 * �û���¼
	 * @param userName
	 * @param password
	 * @return
	 */
	public Token clientLogin(String userName,String password);
	
	/**
	 * �û�ע��
	 * @param clientName
	 * @param password
	 * @param phone
	 * @return
	 */
	public int signup(String clientName,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	
	/**
	 * ͨ��username�����û�
	 * @param userName
	 * @return
	 */
	public Client getClientByUserName(String userName);
	
	/**
	 * ���ǩ��
	 * @param userId
	 * @param uri
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public boolean checkSign(int userId,String uri,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;
	
	/**
	 * �û��ǳ�
	 * @param userId
	 */
	public void logout(int userId);
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
	public List<ReturnFeed> findPublicFeedsByTime(Timestamp time);
	
	/**
	 * �ҳ�����Ϊdate�����е�feed
	 * @param date
	 * @return
	 */
	public List<Feed> getTodayFeedList(Date date);
	
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
	
	/*
	 * 
	 * Admin
	 */
/**
 * web�˹���Ա��½
 * @param adminName
 * @param password
 * @return
 */
	public boolean managerLogin(String adminName,String password);
	/*
	 * 
	 * Follow
	 */
	/**
	 * ��ȡidΪuserid���������ѵ���Ϣ
	 * @param userid
	 * @return
	 */
	public List<Client> getMyFriendInformationById(int userid);
	
	/**
	 * ��ȡidΪuserid�����й�ע���˵���Ϣ
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowingInformationById(int userid);
	
	/**
	 * ��ȡidΪuserid������follower����Ϣ
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowerInformationById(int userid);
	
	/**
	 * ��עĳ��
	 * @param userid��ע��id
	 * @param followid����ע��id
	 * @return
	 */
	public String followSomeone(int userid,int followid);
	
	/**
	 * ȡ����עĳ��
	 * @param userid��ע��id
	 * @param followid����ע��id
	 * @return
	 */
	public String unFollowSomeone(int userid,int followid);
	
	/**
	 * ��ȡĳ�˵���Ϣ
	 * @param userId
	 * @return
	 */
	public ReturnUserInfo getSomeoneInfo(int userId);
	/*
	 * best feed
	 * 
	 */

	/**
	 * ��idΪfeedId�ķ�������Ϊ��ѷ���
	 * @param feedId
	 * @return
	 * @throws ParseException
	 */
	public String setBestFeed(String feedId) throws ParseException;
	
	/**
	 * 
	 * transfer
	 * 
	 */
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds);
}
