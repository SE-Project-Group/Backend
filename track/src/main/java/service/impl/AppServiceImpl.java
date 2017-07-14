package service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;


import dao.ClientDao;
import dao.FeedRepository;
import dao.FollowDao;
import dao.ManagerDao;
import dao.TokenDao;
import model.Client;
import model.Feed;
import model.Follow;
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
	private FollowDao followDao;
	
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
	public  FeedRepository getFeedRepository() {
		return feedRepository;
	}

	public void setFeedRepository(FeedRepository feedRepository) {
		this.feedRepository = feedRepository;
	}
	public  FollowDao getFollowDao() {
		return followDao;
	}

	public void setfollowDao(FollowDao followDao) {
		this.followDao = followDao;
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
	public List<Feed> getTodayFeedList(Date date){
		List<Feed>feeds= feedRepository.GetTodayFeedList(date);
		
		return feeds;
	}
	@SuppressWarnings("null")
	@Override
	public List<Feed> getFriendFeedList(Timestamp time,int userid) {
		List<Follow> follows=followDao.getFriendById(userid);
		
		int friendnum=follows.size();
		int[] friend=new int[friendnum];
		for(int i=0;i<follows.size();i++){
			Follow follow=follows.get(i);
			friend[i]=follow.getFollowId();
		   }
		List<Feed>feeds= feedRepository.findFeedsByTime(time);
		
		for(int i=0;i<feeds.size();i++){
			Feed feed=feeds.get(i);
			int feeduserid=feed.getUserId();
			boolean result=false;
			for(int j=0;j<friendnum;j++){
				if(friend[j]==feeduserid)result=true;
			}
			if(result==false){
				feeds.remove(i);
				i--;
			}
		}
		return feeds;
	}
	@Override
	public List<Feed> getFollowingFeedList(Timestamp time,int userid) {
		List<Follow> follows=followDao.getMyFollowingById(userid);
		int follownum=follows.size();
		int[] following=new int[follownum];
		
		for(int i=0;i<follownum;i++){
			Follow follow=follows.get(i);
			following[i]=follow.getFollowId();
		
		   }
		//System.out.print(follownum);
		List<Feed>feeds= feedRepository.findFeedsByTime(time);		
		for(int i=0;i<feeds.size();i++){
			//System.out.println("feednum:"+feeds.size());
			Feed feed=feeds.get(i);
			int feeduserid=feed.getUserId();
			//System.out.println("feeduserid:"+feeduserid);
			boolean result=false;
			for(int j=0;j<follownum;j++){
				if(following[j]==feeduserid)result=true;
				//System.out.println("following:"+following[j]);
			}
			//System.out.println("result:"+result);
			if(result==false){
				feeds.remove(i);
				i--;
			}
		}
		return feeds;
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
		/*
	 * 
	 * Admin
	 */
	
	@Override
	public boolean managerLogin(String adminName, String password) {
		return managerDao.checkLogin(adminName, password);
	}

	

	@Override
	public List<Client> getMyFriendInformationById(int userid) {
		// TODO Auto-generated method stub
		List<Follow>friends=followDao.getFriendById(userid);
		int friendnum=friends.size();
		int[] friend=new int[friendnum];
		
		for(int i=0;i<friendnum;i++){
			Follow f=friends.get(i);
			friend[i]=f.getFollowId();
		
		   }
		List<Client> result=new ArrayList<Client>();
		for(int j=0;j<friendnum;j++){
			Client client=getClientById(friend[j]);
			result.add(client);
		}
		return result;
	}

	@Override
	public List<Client> getMyFollowingInformationById(int userid) {
		// TODO Auto-generated method stub
		List<Follow>follows=followDao.getMyFollowingById(userid);
		int  follownum=follows.size();
		int[] follow=new int[follownum];
	
		for(int i=0;i<follownum;i++){
			Follow  f=follows.get(i);
			 follow[i]=f.getFollowId();
	
		   }
		List<Client> result=new ArrayList<Client>();
		for(int j=0;j< follownum;j++){
			Client client=getClientById( follow[j]);
			result.add(client);
		}
		return result;
	}

	@Override
	public List<Client> getFollowingMeInformationById(int userid) {
		List<Follow>follows=followDao.getFollowingMeById(userid);
		int  follownum=follows.size();
		if(follownum!=0){
		int[] follow=new int[follownum];
		int tmp=0;
		for(int i=0;i<follownum;i++){
			Follow  f=follows.get(i);
			 follow[i]=f.getUserId();
	

		   }
		
		List<Client> result=new ArrayList<Client>();
		for(int j=0;j< follownum;j++){
			tmp=follow[j];
		
			Client client=getClientById(tmp);
			result.add(client);
		}
		return result;
	}
		else 
			return null;}

	@Override
	public String followSomeone(int userId, int followId) {
		// TODO Auto-generated method stub
		if(followDao.isFollowed(userId, followId)!=null) return"Already Followed";
		Follow newFollow=new Follow();
		newFollow.setFollowId(followId);
		newFollow.setUserId(userId);
		boolean isFriend=followDao.isFollowed(followId, userId)==null?false:true;
		if (isFriend==true) {newFollow.setIsFriend(1);
		Follow updateFollow=new Follow();
		updateFollow.setFollowId(userId);
		updateFollow.setIsFriend(1);
		updateFollow.setUserId(followId);
		followDao.update(updateFollow);}
		else newFollow.setIsFriend(0);
		followDao.insert(newFollow);
		return "success";
		
	}

	@Override
	public String unFollowSomeone(int userId, int followId) {
		// TODO Auto-generated method stub
		
		Follow deleteFollow=followDao.isFollowed(userId, followId);
		if(deleteFollow==null) return"You not Followed This Guy";
	    int isFriend=deleteFollow.getIsFriend();
	    followDao.delete(deleteFollow);
	    if(isFriend==1)
	    {
	    	Follow updateFollow=new Follow();
	        updateFollow.setFollowId(userId);
		    updateFollow.setIsFriend(0);
		    updateFollow.setUserId(followId);
		followDao.update(updateFollow);
		}
		
		
		
		return "success";
	}

}
