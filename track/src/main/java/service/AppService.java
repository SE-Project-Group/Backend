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
import model.Location;
import model.ReturnFollow;
import model.Token;

public interface AppService {
	
	/*
	 * 
	 * Client
	 */
	
	public void insertClient(Client client);
	public void updateClient(Client client);
	public void deleteClient(Client client);
	public Client getClientById(int id);
	public List<Client> getAllClients();
	public Token clientLogin(String userName,String password);
	public int signup(String clientName,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	public Client getClientByUserName(String userName);
	public boolean checkSign(int userId,String uri,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;
	public void logout(int userId);
	/*
	 * 
	 * Feed
	 */
	public void newFeed(Feed feed);
	public void updateFeed(Feed feed);
	public void removeFeed(String _id);
	public List<Feed>findFeedByUserId(int userId);
	public List<Feed>findFeedAround(double longitude,double latitude,double radius);
	public List<Feed> findPublicFeedsByTime(Timestamp time);
	public List<Feed> getTodayFeedList(Date date);
	public List<Feed> getFriendFeedList(Timestamp time,int userid);
	public List<Feed> getAllFeedList(Timestamp time);
	public List<Feed> getFollowingFeedList(Timestamp time,int userid);
	/*like*/
	public int incLikeFeed(String _id,int userId);
	/*comment*/
	public int newComment(String _id,int userId,String text,int replyId);
	
	/*
	 * 
	 * Admin
	 */

	public boolean managerLogin(String adminName,String password);
	/*
	 * 
	 * Follow
	 */
	public List<ReturnFollow> getMyFriendInformationById(int userid);
	public List<Client> getMyFollowingInformationById(int userid);
	public List<Client> getFollowingMeInformationById(int userid);
	public String followSomeone(int userid,int followid);
	public String unFollowSomeone(int userid,int followid);
	
	/*
	 * best feed
	 * 
	 */
	public String setBestFeed(String feedId) throws ParseException;
}
