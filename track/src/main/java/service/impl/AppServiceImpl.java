package service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;


import dao.ClientDao;
import dao.FeedRepository;
import dao.ManagerDao;
import dao.TokenDao;
import model.Client;
import model.Feed;
import model.Token;
import redis.clients.jedis.Jedis;
import service.AppService;

import java.util.UUID;

public class AppServiceImpl implements AppService{
	
	private ClientDao clientDao;
	
	private ManagerDao managerDao;
	
	private TokenDao tokenDao;
	private FeedRepository feedRepository;
	

	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}
	
	public TokenDao getTokenDao() {
		return tokenDao;
	}

	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}
	public FeedRepository getFeedRepository() {
		return feedRepository;
	}

	public void setFeedRepository(FeedRepository feedRepository) {
		this.feedRepository = feedRepository;
	}
	/*
	 * 
	 * 
	 * client
	 */
	@Override
	public void insertClient(Client client) {
		clientDao.insert(client);
	}

	@Override
	public void updateClient(Client client) {
		clientDao.update(client);
	}

	@Override
	public void deleteClient(Client client) {
		clientDao.delete(client);
	}

	@Override
	public Client getClientByID(int ID) {
		return clientDao.getClientByID(ID);
	}

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}
	
	@Override
	public Token clientLogin(String user_name, String password) {
		if(clientDao.checkLogin(user_name, password)){
			Client client=clientDao.getClientByUser_name(user_name);
			return tokenDao.createToken(client.getUser_ID());
		}
		return null;
	}

	@Override
	public int checkSignup(String client_name,String password,String phone) {
		return clientDao.checkSignup(client_name, password, phone);
	}
	
	@Override
	public Client getClientByUser_name(String user_name) {
		return clientDao.getClientByUser_name(user_name);
	}
	
	public boolean checkSign(int user_ID,String uri,String token) throws NoSuchAlgorithmException{
		if(tokenDao.checkSign(user_ID, uri,token))return true;
		return false;
	}
	
	public void logout(int user_ID){
		tokenDao.deleteToken(user_ID);
	}
	/*
	 * 
	 * feed
	 */
	public void NewFeed(Feed feed){
		feedRepository.insert(feed);
	}
	
	/*
	 * 
	 * Admin
	 */
	
	@Override
	public boolean managerLogin(String admin_name, String password) {
		return managerDao.checkLogin(admin_name, password);
	}





	

	

	


	

}
