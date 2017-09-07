package service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import model.ReturnComment;
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
	public Feed getFeed(String feedId){
		return feedRepository.findOne(feedId);
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
		List<Feed>res=feedRepository.findPublicFeedsByUserId(userId);
		Collections.reverse(res);
		return res;
	}

	@Override
	public List<ReturnFeed> findFeedAround(double longitude, double latitude, double radius,int userId) {
		// TODO Auto-generated method stub
		Point point = new Point(longitude,latitude);   
        Distance distance = new Distance(radius,Metrics.KILOMETERS);  
        Circle circle = new Circle(point,distance);   
        List<Feed>feeds= feedRepository.findFeedsAround(circle);
        return feedToReturnFeed(feeds,userId);
	}
	@Override
	public List<ReturnFeed>findFeedAroundSpecUser(double longitude,double latitude,double radius,int userId){
		Point point = new Point(longitude,latitude);   
        Distance distance = new Distance(radius,Metrics.KILOMETERS);  
        Circle circle = new Circle(point,distance);   
        List<Feed>feeds= feedRepository. findFeedsAroundSpecUser(circle,userId);
        return feedToReturnFeed(feeds,userId);
	}
	@Override
	public List<ReturnFeed> findPublicFeedsAfterTime(Timestamp time,int userId) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findPublicFeedsAfterTime(time);
		Collections.reverse(feeds);
		return feedToReturnFeed(feeds,userId);
	}

	@Override
	public List<ReturnFeed> findPublicFeedsBeforeTime(Timestamp time,int userId) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findPublicFeedsBeforeTime(time);
		Collections.reverse(feeds);
		return feedToReturnFeed(feeds,userId);
	}

	@Override
	public List<Feed> getFeedsLoggedIn(int userId, int who) {
		// TODO Auto-generated method stub
		if(userId==who){		//self
			List<Feed>res=findFeedByUserId(who);
			Collections.reverse(res);
			return res;
		}
		else if(followDao.isFriend(userId,who)){
			List<Feed>list1=feedRepository.findPublicFeedsByUserId(who);
			List<Feed>list2=feedRepository.findFriendFeedsByUserId(who);
			for(int i=0;i<list1.size();i++){
				list2.add(list1.get(i));
			}
			return sortFeed(list2);
		}
		else{
			List<Feed>res=findPublicFeedsById(who);
			Collections.reverse(res);
			return res;
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
		List<Feed>feeds= feedRepository.findFeedsAfterTime(time);
		
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
		Collections.reverse(feeds);
		return feeds;
	}

	@Override
	public List<Feed> getAllFeedList(Timestamp time) {
		// TODO Auto-generated method stub
		List<Feed>feeds= feedRepository.findFeedsAfterTime(time);
		Collections.reverse(feeds);
		return feeds;
	}

	@Override
	public List<Feed> getFollowingFeedsAfterTime(Timestamp time, int userid) {
		// TODO Auto-generated method stub
		List<Follow> follows=followDao.getFollowingById(userid);
		int follownum=follows.size();
		int[] following=new int[follownum];
		
		for(int i=0;i<follownum;i++){
			Follow follow=follows.get(i);
			following[i]=follow.getFollowId();
		
		   }
		//System.out.print(follownum);
		List<Feed>feeds= feedRepository.findFeedsAfterTime(time);	
		List<Feed>res=new ArrayList<Feed>();
		int count=0;
		for(int i=feeds.size()-1;i>=0;i--){
			Feed feed=feeds.get(i);
			if(!feed.getShareArea().equals("public")){
				continue;
			}
			int feeduserid=feed.getUserId();
			boolean result=false;
			for(int j=0;j<follownum;j++){
				if(following[j]==feeduserid)result=true;
			}
			if(result==true){
				res.add(feed);
				count++;
			}
			if(count==20){
				break;
			}
		}
		return res;
	}
	
	public List<Feed> getFollowingFeedsBeforeTime(Timestamp time, int userid) {
		List<Follow> follows=followDao.getFollowingById(userid);
		int follownum=follows.size();
		int[] following=new int[follownum];
		
		for(int i=0;i<follownum;i++){
			Follow follow=follows.get(i);
			following[i]=follow.getFollowId();
		
		}
		List<Feed>feeds= feedRepository.findFeedsBeforeTime(time);		
		List<Feed>res=new ArrayList<Feed>();
		int count=0;
		for(int i=feeds.size()-1;i>=0;i--){
			Feed feed=feeds.get(i);
			if(!feed.getShareArea().equals("public")){
				continue;
			}
			int feeduserid=feed.getUserId();
			boolean result=false;
			for(int j=0;j<follownum;j++){
				if(following[j]==feeduserid)result=true;
			}
			if(result==true){
				res.add(feed);
				count++;
			}
			if(count==20){
				break;
			}
		}
		return res;
	}
	
/*	@Override
	public List<ReturnShareFeed> getFollowingShareFeedList(Timestamp time,int userId){
		List<Follow> follows=followDao.getFollowingById(userId);
		int follownum=follows.size();
		int[] following=new int[follownum];
		
		for(int i=0;i<follownum;i++){
			Follow follow=follows.get(i);
			following[i]=follow.getFollowId();
		}
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
	}*/

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
	public void decLikeFeed(String _id, int userId) {
		Feed feed=feedRepository.findOne(_id);
		int likeCount=feed.getLikeCount()-1;
		feed.setLikeCount(likeCount);
		List<Like> likelist=feed.getLikeList();
		for(Like like:likelist){
			if(like.getUserId()==userId){
				likelist.remove(like);
				break;
			}
		}
		feed.setLikeList(likelist);
		feedRepository.update(feed);
	}

	@Override
	public String newComment(String _id, int userId, String text, int replyId) {
		// TODO Auto-generated method stub
		Feed feed=feedRepository.findOne(_id);
		int commentcount=feed.getCommentCount()+1;
		feed.setCommentCount(commentcount);
		List<Comment> commentList=feed.getCommentList();
		Comment newcomment=new Comment(userId,replyId,text);
		int commentid=feed.getCommentCount();
		newcomment.setCommentId(commentid);
		commentList.add(newcomment);
		feed.setCommentList(commentList);
		feedRepository.update(feed);
		int feeduserid=feed.getUserId();
		return Integer.toString(feeduserid)+","+Integer.toString(commentid);
	}
	
	@Override
	public List<Comment> findCommentList(String feedId) {
		Feed feed=feedRepository.findOne(feedId);
		return feed.getCommentList();
	}
	
	@Override
	public boolean shareFeed(int userId, String feedId,String text) {
		if(getFeed(feedId)==null){
			return false;
		}
		Feed feed=new Feed();
		feed.setUserId(userId);
		feed.setText(text);
		feed.setShareId(feedId);
		feed.setShareArea("public");
		Feed shareFeed=feedRepository.findOne(feedId);
		int shareCnt=shareFeed.getShareCount();
		shareFeed.setShareCount(shareCnt+1);
		feedRepository.update(shareFeed);
		feedRepository.insert(feed);
		return true;
	}

	@Override
	public List<ReturnFeed> feedToReturnFeed(List<Feed> feeds,int userid) {
		// TODO Auto-generated method stub
		List<ReturnFeed>res=new ArrayList<ReturnFeed>();
        SignedUrlFactory signedUrlFactory = new SignedUrlFactory();
		for(int i=0;i<feeds.size();i++){
			Feed curFeed=feeds.get(i);
			String shareId=curFeed.getShareId();
			if(!shareId.equals("")){	
				int userId=curFeed.getUserId();
				Client client=clientDao.getClientById(userId);
				String portrait=signedUrlFactory.getPortraitUrl(userId);
				
				
				Feed shareFeed=getFeed(shareId);
				String shareText=shareFeed.getText();
				int shareUserId=shareFeed.getUserId();
				Client shareClient=clientDao.getClientById(shareUserId);
				String shareUserName=shareClient.getUserName();
				List<Like> likelist=new ArrayList<Like>();
				likelist=shareFeed.getLikeList();
				boolean liked=false;
				int size=likelist.size();
				for(int j=0;j<size;j++){
					if(likelist.get(j).getUserId()==userid)
						liked=true;
				}
				ReturnFeed returnFeed=new ReturnFeed();
				returnFeed.setFeed_id(curFeed.get_id());
				returnFeed.setPicUrls(signedUrlFactory.getPicUrls(shareFeed.get_id(), shareFeed.getPicCount()));
				returnFeed.setOwner_id(userId);
				returnFeed.setOwner_name(client.getUserName());
				returnFeed.setText(curFeed.getText());
				returnFeed.setDate(curFeed.getTime());
				returnFeed.setLiked(liked);
				returnFeed.setLike_cnt(shareFeed.getLikeCount());
				returnFeed.setShare_cnt(shareFeed.getShareCount());
				returnFeed.setComment_cnt(curFeed.getCommentCount());;
				returnFeed.setPic_cnt(shareFeed.getPicCount());
				returnFeed.setPortrait_url(portrait);
				returnFeed.setShare_feed_id(shareId);
				returnFeed.setShare_owner_id(shareUserId);
				returnFeed.setShare_owner_name(shareUserName);
				returnFeed.setShare_text(shareText);
				res.add(returnFeed);
			}
			else{
				List<Like> likelist=new ArrayList<Like>();
				likelist=curFeed.getLikeList();
				boolean liked=false;
				int size=likelist.size();
				for(int j=0;j<size;j++){
					if(likelist.get(j).getUserId()==userid)
						liked=true;
				}
				ReturnFeed returnFeed=new ReturnFeed();
				Client client=clientDao.getClientById(curFeed.getUserId());
				returnFeed.setFeed_id(curFeed.get_id());
				returnFeed.setPicUrls(signedUrlFactory.getPicUrls(curFeed.get_id(), curFeed.getPicCount()));
				returnFeed.setOwner_id(client.getUserId());
				returnFeed.setOwner_name(client.getUserName());
				returnFeed.setText(curFeed.getText());
				returnFeed.setDate(curFeed.getTime());
				returnFeed.setLiked(liked);
				returnFeed.setLike_cnt(curFeed.getLikeCount());
				returnFeed.setShare_cnt(curFeed.getShareCount());
				returnFeed.setComment_cnt(curFeed.getCommentCount());;
				returnFeed.setPic_cnt(curFeed.getPicCount());
				returnFeed.setPosition(curFeed.getPosition());
				returnFeed.setLatitude(curFeed.getLocation().getLatitude());
				returnFeed.setLongitude(curFeed.getLocation().getLongitude());
				returnFeed.setPortrait_url(signedUrlFactory.getPortraitUrl(curFeed.getUserId()));
				res.add(returnFeed);
			}
		}
		 return res;
	}

	

	@Override
	public List<ReturnComment> commentToReturnComment(List<Comment> comments) {
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
		List<ReturnComment> res=new ArrayList<ReturnComment>();
		for(Comment comment:comments){
			int user_id=comment.getUserId();
			String portrait_url=signedUrlFactory.getPortraitUrl(user_id);
			String user_name=clientDao.getClientById(user_id).getUserName();
			String comment_text=comment.getText();
			int reply_id=comment.getReplyId();
			String time=comment.getTime();
			int comment_id=comment.getCommentId();
			ReturnComment returnComment=new ReturnComment(portrait_url,user_id,user_name,comment_text,reply_id,time,comment_id);
			res.add(returnComment);
		}
		return res;
	}

	public List<Feed> searchFeed(String query){
		List<Feed> feeds=feedRepository.searchFeed(query);
		Collections.reverse(feeds);
		return feeds;
	}

	@Override
	public String getOriginPhoto(String fileName) {
		return feedRepository.getOriginPhoto(fileName);
	}

	@Override
	public String getBigPhotoUrls(String feedId) {
		Feed feed=getFeed(feedId);
		if(feed==null){
			return "failed";
		}
		int picCount=feed.getPicCount();
		List<String>list=feedRepository.getBigPhotoUrls(feedId,picCount);
		String res="";
		if(list.size()==0){
			return res;
		}
		res=list.get(0);
		for(int i=1;i<list.size();i++){
			res=res+","+list.get(i);
		}
		return res;
	}

	@Override
	public List<ReturnFeed> myLikeFeeds(int userid) {
		List<Feed>feeds= feedRepository.findAll();
		for(int i=0;i<feeds.size();i++){
			Feed tmp=feeds.get(i);
			int result=0;
			List<Like>likelist=tmp.getLikeList();
			for (int j=0;j<tmp.getLikeCount();j++){
				
			if(	likelist.get(j).getUserId()==userid)
				result=1;
			}
			if(result==0){
				feeds.remove(i);
				i--;
			}
			else{}
		}
		Collections.reverse(feeds);
		return feedToReturnFeed(feeds,userid);
	}

	@Override
	public List<ReturnFeed> myCommentFeeds(int userid) {
		List<Feed>feeds= feedRepository.findAll();
		for(int i=0;i<feeds.size();i++){
			Feed tmp=feeds.get(i);
			int result=0;
			List<Comment>commentlist=tmp.getCommentList();
			for (int j=0;j<tmp.getCommentCount();j++){
				
			if(	commentlist.get(j).getUserId()==userid)
				result=1;
			}
			if(result==0){
				feeds.remove(i);
				i--;
			}
			else{}
		}
		Collections.reverse(feeds);
		return feedToReturnFeed(feeds,userid);
	}

	@Override
	public List<ReturnFeed> myShareFeeds(int userId) {
		List<Feed>feeds=feedRepository.findByUserId(userId);
		for(int i=0;i<feeds.size();i++){
			if(feeds.get(i).getShareId().equals("")){
				feeds.remove(i);
				i--;
			}
		}
		Collections.reverse(feeds);
		return feedToReturnFeed(feeds,userId);
	}

	@Override
	public List<Feed> sortFeed(List<Feed> feeds) {
		Collections.sort(feeds, new Comparator<Feed>() {
            public int compare(Feed feed1,Feed feed2) {
            	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
					Date time1=df.parse(feed1.getTime());
					Date time2=df.parse(feed2.getTime());
					if(time1.getTime()>time2.getTime()){
						return 0;
					}
					else return 1;
				} catch (ParseException e) {
					return 0;
				}
            }
        });
		return feeds;
	}

	
}
