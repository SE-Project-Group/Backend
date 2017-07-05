package service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.data.geo.Point;

import model.Client;
import model.Feed;
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
	public boolean checkSign(int user_ID,String uri,String token)throws NoSuchAlgorithmException;
	public void logout(int user_ID);
	/*
	 * 
	 * Feed
	 */
	public void NewFeed(Feed feed);
	public void UpdateFeed(Feed feed);
	public void removeFeed(String user_id, long time);
	public List<Feed>findFeedByUser_id(String user_id);
	public List<Point>findPointAround(double longitude,double latitude,double radius);
	/*
	 * 
	 * Admin
	 */
	
	public boolean managerLogin(String admin_name,String password);
}
