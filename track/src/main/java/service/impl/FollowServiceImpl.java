package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.ClientDao;
import dao.FeedRepository;
import dao.FollowDao;
import model.Client;
import model.Feed;
import model.Follow;
import model.ReturnFollow;
import model.ReturnUserInfo;
import model.SignedUrlFactory;
import service.FollowService;

public class FollowServiceImpl implements FollowService{
private ClientDao clientDao;
	
	private FollowDao followDao;
	
	private FeedRepository feedRepository;
	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
		
	}
	public  FollowDao getFollowDao() {
		return followDao;
	}

	public void setfollowDao(FollowDao followDao) {
		this.followDao = followDao;
	}
	public  FeedRepository getFeedRepository() {
		return feedRepository;
	}

	public void setFeedRepository(FeedRepository feedRepository) {
		this.feedRepository = feedRepository;
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
			Client client=clientDao.getClientById(friend[j]);
			result.add(client);
		}
		return result;
	}

	@Override
	public List<ReturnFollow> getFollowingInformationById(int userId,int who) {
		List<ReturnFollow>res=new ArrayList<ReturnFollow>();
		List<Follow>follows=followDao.getFollowingById(who);
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory(); 
		for(int i=0;i<follows.size();i++){
			Follow follow=follows.get(i);
			int followId=follow.getFollowId();
			String url=signedUrlFactory.getPortraitUrl(followId);
			String userName=clientDao.getClientById(followId).getUserName();
			String state= followDao.getRelationship(userId, followId);
			ReturnFollow rf=new ReturnFollow(userName,url,followId,state);
			res.add(rf);
		}
		return res;
	}

	@Override
	public List<ReturnFollow> getFollowerInformationById(int userId,int who) {
		List<ReturnFollow>res=new ArrayList<ReturnFollow>();
		List<Follow>follows=followDao.getFollowerById(who);
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory(); 
		for(int i=0;i<follows.size();i++){
			Follow follow=follows.get(i);
			int followerId=follow.getUserId();
			String url=signedUrlFactory.getPortraitUrl(followerId);
			String userName=clientDao.getClientById(followerId).getUserName();
			String state= followDao.getRelationship(userId, followerId);
			ReturnFollow rf=new ReturnFollow(userName,url,followerId,state);
			res.add(rf);
		}
		return res;
	}

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

	@Override
	public ReturnUserInfo getSomeoneInfo(int userId,int who) {
		String relationship=followDao.getRelationship(userId, who);
		Client client=clientDao.getClientById(who);
		String name=client.getUserName();
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory(); 
		String url=signedUrlFactory.getPortraitUrl(who);
		int follower_cnt=followDao.getFollowerById(who).size();
		int follow_cnt=followDao.getFollowingById(who).size();
		List<Feed> feeds=feedRepository.findByUserId(who);
		int like_cnt=0;
		int share_cnt=0;
		for(int i=0;i<feeds.size();i++){
			Feed feed=feeds.get(i);
			like_cnt+=feed.getLikeCount();
			share_cnt+=feed.getShareCount();
		}
		ReturnUserInfo rui=new ReturnUserInfo(who,name,url,follow_cnt,follower_cnt,like_cnt,share_cnt,relationship);
		return rui;
	}

	@Override
	public String getRelationship(int userId1, int userId2) {
		// TODO Auto-generated method stub
		return followDao.getRelationship(userId1, userId2);
	}

}
