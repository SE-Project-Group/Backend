package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import dao.ClientDao;
import dao.FeedRepository;
import dao.ManagerDao;
import dao.TokenDao;
import model.Client;
import model.Feed;
import model.Like;
import model.Comment;
import model.Location;
import model.Token;
import redis.clients.jedis.Jedis;
import service.AppService;

import java.util.UUID;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
	public Client getClientById(int id) {
		return clientDao.getClientById(id);
	}

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}
	
	@Override
	public Token clientLogin(String userName, String password) {
		if(clientDao.checkLogin(userName, password)){
			Client client=clientDao.getClientByUserName(userName);
			return tokenDao.createToken(client.getUserId());
		}
		return null;
	}

	@Override
	public int signup(String clientName,String password,String phone) {
		int flag=clientDao.checkSignup(clientName, password, phone);
		if(flag==0){
			Client client=new Client();
			client.setUserName(clientName);
			client.setPassword(password);
			client.setPhone(phone);
			clientDao.insert(client);
		}
		return flag;
	}
	
	@Override
	public Client getClientByUserName(String userName) {
		return clientDao.getClientByUserName(userName);
	}
	
	public boolean checkSign(int userId,String uri,String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(tokenDao.checkSign(userId, uri,sign))return true;
		return false;
	}
	
	public void logout(int userId){
		tokenDao.deleteToken(userId);
	}
	/*
	 * 
	 * feed
	 */
	public void newFeed(Feed feed){
		feedRepository.insert(feed);
	}
	public void updateFeed(Feed feed){
		feedRepository.update(feed);
	}
	public void removeFeed(String _id){
		feedRepository.removeOne(_id);
	}
	public List<Feed>findFeedByUserId(int userId){
		return feedRepository.findByUserId(userId);
	}
	public List<Feed>findFeedAround(double longitude,double latitude,double radius){
		Point point = new Point(longitude,latitude);   
        Distance distance = new Distance(radius,Metrics.KILOMETERS);  
        Circle circle = new Circle(point,distance);   
        List<Feed>feeds= feedRepository.findFeedsAround(circle);
        return feeds;
	}
	public List<Feed> findPublicFeedsByTime(Timestamp time){
		 List<Feed>feeds= feedRepository.findPublicFeedsByTime(time);
		 return feeds;
	}
		/*
	 * 
	 * Admin
	 */
	
	@Override
	public boolean managerLogin(String adminName, String password) {
		return managerDao.checkLogin(adminName, password);
	}

	@Override
	public int incLikeFeed(String _id,int userId) {
		// TODO Auto-generated method stub
		Feed feed=feedRepository.findOne(_id);
		int likeCount=feed.getLikeCount()+1;
		feed.setLikeCount(likeCount);
		List<Like> likelist=feed.getLikeList();
		Like newlike=new Like();
		newlike.setUserId(userId);
		likelist.add(newlike);
		feed.setLikeList(likelist);
		feedRepository.update(feed);
		return 1;
	}

	@Override
	public int newComment(String _id,int userId, String text, int replyId) {
		// TODO Auto-generated method stub
		Feed feed=feedRepository.findOne(_id);
		int commentcount=feed.getCommentCount()+1;
		feed.setCommentCount(commentcount);
		List<Comment> commentList=feed.getCommentList();
		Comment newcomment=new Comment(userId,replyId,text);
		newcomment.setCommentId(feed.getCommentCount());
		commentList.add(newcomment);
		feed.setCommentList(commentList);
		feedRepository.update(feed);
		
		
		return 1;
	}





	

	

	


	

}
