package dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.List;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import dao.FeedRepository;
import model.Comment;
import model.Feed;
import model.Like;
import model.Location;
import model.SignedUrlFactory;

public class FeedRepositoryImpl implements FeedRepository{
	 private MongoTemplate mongoTemplate; 
	 
	 public MongoTemplate getMongoTemplate() {
	     return mongoTemplate;
	 }
	 
	 public void setMongoTemplate(MongoTemplate mongoTemplate) {
	     this.mongoTemplate = mongoTemplate;
	 }
	 
	
	 @Override
	public void insert(Feed feed) {
		// TODO Auto-generated method stub
	 mongoTemplate.insert(feed); 
	}

	@Override
	public Feed findOne(String _id) {
		// TODO Auto-generated method stub
		 return mongoTemplate.findOne(new Query(Criteria.where("_id").is(_id)), Feed.class);   
		
	}
    
	@Override
	public List<Feed> findAll() {
		// TODO Auto-generated method stub
        return mongoTemplate.find(new Query(), Feed.class); 
	}

	@Override
	public List<Feed> findByUserId(int userId) {
		// TODO Auto-generated method stub
        return  mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), Feed.class);
	}
	
	@Override
	public List<Feed> findPublicFeedsByUserId(int userId){
		return  mongoTemplate.find(new Query(Criteria.where("userId").is(userId).and("shareArea").is("public")), Feed.class);
	}

	@Override
	public void removeOne(String _id) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("_id").is(_id));
		mongoTemplate.remove(query, Feed.class);
	        }   
	

	@Override
	public void removeAll() {
		mongoTemplate.dropCollection(Feed.class);
	}

	@Override
	public void update(Feed feed) {
		// TODO Auto-generated method stub
		String n_id=feed.get_id();
		int id=feed.getUserId();
	    String ntext=feed.getText();   
	    boolean nshowLocation=feed.getShowLocation();
	    String nshareArea=feed.getShareArea();
	    int npicCount=feed.getPicCount();
	    int nshareCount=feed.getShareCount();
	    int ncommentCount=feed.getCommentCount();
	    int nlikeCount=feed.getLikeCount();
	    List<Like> nlikeList=feed.getLikeList();
	    List<Comment> ncommentList=feed.getCommentList();
	    /*Query query=Query.query(Criteria.where("_id").is(n_id));
	    mongoTemplate.upsert(newQuery(Criteria.where("onumber").is("001")),
	    		newUpdate().set("cname", "zcy"), collectionName); */
	    mongoTemplate.upsert(
	    		new Query(Criteria.where("_id").is(n_id)), 
			 new Update()
			 .set("userId", id)
			 .set("text", ntext)
			 .set("showLocation", nshowLocation)
			 .set("shareArea",nshareArea)
			 .set("picCount", npicCount)
			 .set("shareCount", nshareCount)
			 .set("commentCount", ncommentCount)
			 .set("likeCount", nlikeCount)
			 .set("likeList", nlikeList)
			 .set("commentList", ncommentList)
			 , Feed.class);
	
	}

	@Override
	public List<Feed> findFeedsAround(Circle circle) {
		Query query=Query.query(Criteria.where("location").withinSphere(circle));
		List<Feed>feeds=mongoTemplate.find(query,Feed.class);
		return feeds;
	}
	@Override
	public List<Feed> findFeedsAroundSpecUser(Circle circle,int userId){
		Query query=Query.query(Criteria.where("location").withinSphere(circle).and("userId").is(userId));
		List<Feed>feeds=mongoTemplate.find(query,Feed.class);
		return feeds;
	}
	@Override
	public List<Feed> findPublicFeedsAfterTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("shareArea").is("public").and("time").gt(stime)).limit(20), Feed.class); 
		
		return allfeed;
	}
	@Override
	public List<Feed> findPublicFeedsBeforeTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("shareArea").is("public").and("time").lt(stime)).limit(20), Feed.class); 
		
		return allfeed;
	}

	@Override
	public List<Feed> findFeedsAfterTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("time").gt(stime)), Feed.class); 
		
		return allfeed;
	}
	
	@Override
	public List<Feed> findFeedsBeforeTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("time").lt(stime)), Feed.class); 
		
		return allfeed;
	}
	@Override
	public List<Feed> getTodayFeedList(Date date) {
		
			// TODO Auto-generated method stub
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String stime = df.format(date);
			List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("time").regex(stime+".")), Feed.class); 
			
			return allfeed;
	
	}

	@Override
	public List<Feed> findFriendFeedsByUserId(int userId) {
		return  mongoTemplate.find(new Query(Criteria.where("userId").is(userId).and("shareArea").is("friend")), Feed.class);
	}
	 
   @Override
	public List<Feed> searchFeed(String query){
	   return  mongoTemplate.find(new Query(Criteria.where("text").regex(query)), Feed.class);
	}

	@Override
	public String getOriginPhoto(String fileName) {
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
		return signedUrlFactory.getOriginPhoto(fileName);
	}

	@Override
	public List<String> getBigPhotoUrls(String feedId,int picCount) {
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
		return signedUrlFactory.getBigPicUrls(feedId, picCount);
	}

}
