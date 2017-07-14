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
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String itime=	df.format(new Date(System.currentTimeMillis()));
		// TODO Auto-generated method stub
		String n_id=feed.get_id();
		int id=feed.getUserId();
	    String ntext=feed.getText();   
	    boolean nshowLocation=feed.getShowLocation();
	    Location nlocation=feed.getLocation();
	    String nshareArea=feed.getShareArea();
	    List<Integer> nmentionList=feed.getMentionList();
	    List<Integer> npicList=feed.getPicList();
	    int nshareCount=feed.getShareCount();
	    int ncommentCount=feed.getCommentCount();
	    int nlikeCount=feed.getLikeCount();
	    String nposition=feed.getPosition();
	    List<Like> nlikeList=feed.getLikeList();
	    List<Comment> ncommentList=feed.getCommentList();
	    /*Query query=Query.query(Criteria.where("_id").is(n_id));
	    mongoTemplate.upsert(newQuery(Criteria.where("onumber").is("001")),
	    		newUpdate().set("cname", "zcy"), collectionName); */
	    mongoTemplate.upsert(
	    		new Query(Criteria.where("_id").is(n_id)), 
			 new Update()
			 .set("_id", n_id)
			 .set("userId", id)
			 .set("time", itime)
			 .set("text", ntext)
			 .set("showLocation", nshowLocation)
			 .set("location", nlocation)
			 .set("shareArea",nshareArea)
			 .set("mentionList", nmentionList)
			 .set("picList", npicList)
			 .set("shareCount", nshareCount)
			 .set("commentCount", ncommentCount)
			 .set("likeCount", nlikeCount)
			 .set("position", nposition)
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
	public List<Feed> findPublicFeedsByTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("shareArea").is("public").and("time").gt(stime)), Feed.class); 
		
		return allfeed;
	}

	@Override
	public List<Feed> findFeedsByTime(Timestamp time) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = df.format(time);
		List<Feed> allfeed=	mongoTemplate.find(new Query(Criteria.where("time").gt(stime)), Feed.class); 
		
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
	 

		

}
