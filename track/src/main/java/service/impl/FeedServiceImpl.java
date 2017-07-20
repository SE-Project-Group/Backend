package service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import dao.ClientDao;
import dao.FeedRepository;
import dao.FollowDao;
import model.Client;
import model.Comment;
import model.Feed;
import model.Follow;
import model.Like;
import model.ReturnFeed;
import model.SignedUrlFactory;
import service.FeedService;

public class FeedServiceImpl implements FeedService{
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
	public void newFeed(Feed feed) {
		// TODO Auto-generated method stub
		feedRepository.insert(feed);
	}

	@Override
	public void updateFeed(Feed feed) {
		// TODO Auto-generated method stub
		feedRepository.update(feed);
		
	}

	@Override
	public void removeFeed(String _id) {
		// TODO Auto-generated method stub
		feedRepository.removeOne(_id);
	}

	@Override
	public List<Feed> findFeedByUserId(int userId) {
		// TODO Auto-generated method stub
		return feedRepository.findByUserId(userId);
	}

	@Override
	public List<Feed> findPublicFeedsById(int userId) {
		// TODO Auto-generated method stub
		return feedRepository.findPublicFeedsByUserId(userId);
	}

	@Override
	public List<ReturnFeed> findFeedAround(double longitude, double latitude, double radius) {
		// TODO Auto-generated method stub
		Point point = new Point(longitude,latitude);   
        Distance distance = new Distance(radius,Metrics.KILOMETERS);  
        Circle circle = new Circle(point,distance);   
        List<Feed>feeds= feedRepository.findFeedsAround(circle);
        return feedToReturnFeed(feeds);
	}

	@Override
	public List<ReturnFeed> findPublicFeedsAfterTime(Timestamp time) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findPublicFeedsAfterTime(time);
		return feedToReturnFeed(feeds);
	}

	@Override
	public List<ReturnFeed> findPublicFeedsBeforeTime(Timestamp time) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findPublicFeedsBeforeTime(time);
		return feedToReturnFeed(feeds);
	}

	@Override
	public List<Feed> getFeedsLoggedIn(int userId, int who) {
		// TODO Auto-generated method stub
		if(userId==who){		//self
			return findFeedByUserId(who);
		}
		else if(followDao.isFriend(userId,who)){
			List<Feed>list1=feedRepository.findPublicFeedsByUserId(who);
			List<Feed>list2=feedRepository.findFriendFeedsByUserId(who);
			for(int i=0;i<list1.size();i++){
				list2.add(list1.get(i));
			}
			return list2;
		}
		else{
			return findPublicFeedsById(who);
		}
	}

	@Override
	public List<Feed> getFriendFeedList(Timestamp time, int userid) {
		// TODO Auto-generated method stub
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
			else if(feed.getShareArea().equals("private")){
				feeds.remove(i);
				i--;
			}
		}
		return feeds;
	}

	@Override
	public List<Feed> getAllFeedList(Timestamp time) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findFeedsByTime(time);
		return feeds;
	}

	@Override
	public List<Feed> getFollowingFeedList(Timestamp time, int userid) {
		// TODO Auto-generated method stub
		List<Follow> follows=followDao.getFollowingById(userid);
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
			else if(!feed.getShareArea().equals("public")){
				feeds.remove(i);
				i--;
			}
		}
		return feeds;
	}

	@Override
	public int incLikeFeed(String _id, int userId) {
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
		int userid=feed.getUserId();
		return userid;
	}

	@Override
	public int newComment(String _id, int userId, String text, int replyId) {
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
		int feeduserid=feed.getUserId();
		return feeduserid;
	}

	@Override
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds) {
		// TODO Auto-generated method stub
		List<ReturnFeed>res=new ArrayList<ReturnFeed>();
        SignedUrlFactory factory = new SignedUrlFactory();
		for(int i=0;i<feeds.size();i++){
			Feed curFeed=feeds.get(i);
			ReturnFeed returnFeed=new ReturnFeed();
			Client client=clientDao.getClientById(curFeed.getUserId());
			returnFeed.setFeed_id(curFeed.get_id());
			returnFeed.setPicUrls(factory.getPicUrls(curFeed.get_id(), curFeed.getPicCount()));
			returnFeed.setOwner_id(client.getUserId());
			returnFeed.setOwner_name(client.getUserName());
			returnFeed.setText(curFeed.getText());
			returnFeed.setDate(curFeed.getTime());
			returnFeed.setLike_cnt(curFeed.getLikeCount());
			returnFeed.setShare_cnt(curFeed.getShareCount());
			returnFeed.setComment_cnt(curFeed.getCommentCount());;
			returnFeed.setPic_cnt(curFeed.getPicCount());
			returnFeed.setPosition(curFeed.getPosition());
			returnFeed.setLatitude(curFeed.getLocation().getLatitude());
			returnFeed.setLongitude(curFeed.getLocation().getLongitude());
			returnFeed.setPortrait_url(factory.getPortraitUrl(curFeed.getUserId()));
			res.add(returnFeed);
		}
		 return res;
	}

}
