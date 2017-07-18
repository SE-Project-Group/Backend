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
	 * 插入用户
	 * @param client
	 */
	public void insertClient(Client client);
	
	/**
	 * 更新用户
	 * @param client
	 */
	public void updateClient(Client client);
	
	/**
	 * 删除用户
	 * @param client
	 */
	public void deleteClient(Client client);
	
	/**
	 * 通过id获取用户
	 * @param id
	 * @return
	 */
	public Client getClientById(int id);
	
	/**
	 * 获取全部用户
	 * @return
	 */
	public List<Client> getAllClients();
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public Token clientLogin(String userName,String password);
	
	/**
	 * 用户注册
	 * @param clientName
	 * @param password
	 * @param phone
	 * @return
	 */
	public int signup(String clientName,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	
	/**
	 * 通过username查找用户
	 * @param userName
	 * @return
	 */
	public Client getClientByUserName(String userName);
	
	/**
	 * 检查签名
	 * @param userId
	 * @param uri
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public boolean checkSign(int userId,String uri,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;
	
	/**
	 * 用户登出
	 * @param userId
	 */
	public void logout(int userId);
	/*
	 * 
	 * Feed
	 */
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
	 * 找出半径为radius，中心为longitude，latitude的范围内的动态
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return
	 */
	public List<ReturnFeed>findFeedAround(double longitude,double latitude,double radius);
	/**
	 * 找出time之后的所有的public的feed
	 * @param time
	 * @return
	 */
	public List<ReturnFeed> findPublicFeedsByTime(Timestamp time);
	
	/**
	 * 找出日期为date的所有的feed
	 * @param date
	 * @return
	 */
	public List<Feed> getTodayFeedList(Date date);
	
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
	public List<Feed> getFollowingFeedList(Timestamp time,int userid);
	/*like*/
	/**
	 * 为id为_id的feed增加赞数并记录下点赞人的userid和点赞时间
	 * @param _id
	 * @param userId
	 * @return被点赞人的userId
	 */
	 
	public int incLikeFeed(String _id,int userId);
	/*comment*/
	/**
	 * 为id为_id的feed加入评论，内容为text，评论人为userid，自动生成评论id，记录评论时间以及回复的谁
	 * @param _id
	 * @param userId
	 * @param text
	 * @param replyId为0为回复的评论，不为0则为回复id为replyId的评论
	 * @return 被评论人的userId
	 */
	public int newComment(String _id,int userId,String text,int replyId);
	
	/*
	 * 
	 * Admin
	 */
/**
 * web端管理员登陆
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
	 * 获取id为userid的所有朋友的信息
	 * @param userid
	 * @return
	 */
	public List<Client> getMyFriendInformationById(int userid);
	
	/**
	 * 获取id为userid的所有关注的人的信息
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowingInformationById(int userid);
	
	/**
	 * 获取id为userid的所有follower的信息
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowerInformationById(int userid);
	
	/**
	 * 关注某人
	 * @param userid关注人id
	 * @param followid被关注人id
	 * @return
	 */
	public String followSomeone(int userid,int followid);
	
	/**
	 * 取消关注某人
	 * @param userid关注人id
	 * @param followid被关注人id
	 * @return
	 */
	public String unFollowSomeone(int userid,int followid);
	
	/**
	 * 获取某人的信息
	 * @param userId
	 * @return
	 */
	public ReturnUserInfo getSomeoneInfo(int userId);
	/*
	 * best feed
	 * 
	 */

	/**
	 * 将id为feedId的分享设置为最佳分享
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
