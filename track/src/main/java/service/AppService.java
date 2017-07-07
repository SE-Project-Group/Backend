package service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.geo.Point;

import model.Client;
import model.Feed;
import model.Location;
import model.Token;

public interface AppService {
	
	/*
	 * 
	 * Client
	 */
	
	public void insertClient(Client client);
	public void updateClient(Client client);
	public void deleteClient(Client client);
	public Client getClientByID(int ID);
	public List<Client> getAllClients();
	public Token clientLogin(String user_name,String password);
	public int checkSignup(String client_name,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	public Client getClientByUser_name(String user_name);
	public boolean checkSign(int user_ID,String uri,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;
	public void logout(int user_ID);
	/*
	 * 
	 * Feed
	 */
	public void NewFeed(Feed feed);
	public void UpdateFeed(Feed feed);
	public void removeFeed(String _id);
	public List<Feed>findFeedByUser_id(int user_id);
	public List<Feed>findFeedAround(double longitude,double latitude,double radius);
	/*like*/
	public int incLikeFeed(String _id);
	/*
	 * 
	 * Admin
	 */
	
	public boolean managerLogin(String admin_name,String password);
}
