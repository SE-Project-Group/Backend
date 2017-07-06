package dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import dao.FeedRepository;
import model.Feed;
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
	public Feed findOne(String user_id, String time) {
		// TODO Auto-generated method stub
		 return mongoTemplate.findOne(new Query(Criteria.where("user_id").is(user_id).and("time").is(time)), Feed.class);   
		
	}

	@Override
	public List<Feed> findAll() {
		// TODO Auto-generated method stub
        return mongoTemplate.find(new Query(), Feed.class); 
	}

	@Override
	public List<Feed> findByUser_id(String user_id) {
		// TODO Auto-generated method stub
        return  mongoTemplate.find(new Query(Criteria.where("user_id").is(user_id)), Feed.class);
	}

	@Override
	public void removeOne(String user_id, String time) {
		// TODO Auto-generated method stub
		Query query = Query.query(Criteria.where("user_id").is(user_id).and("time").is(time));
		mongoTemplate.remove(query, Feed.class);
	        }   
	

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		/* List<Feed> list = this.findAll();   
	        if(list != null){   
	            for(Feed feed : list){   
	            	String pstring= feed.toString();
	            	 System.out.println(pstring);
	               mongoTemplate.remove(feed);   
	            }   
	        }   */
		mongoTemplate.dropCollection(Feed.class);
	}

	@Override
	public void update(Feed feed) {
		// TODO Auto-generated method stub
		int id=feed.getUser_id();
		String itime=feed.getTime();
	    String ntext=feed.getText();   
	    boolean nshowLocation=feed.getShowLocation();
	    Location nlocation=feed.getLocation();
	    String nshareArea=feed.getShareArea();
	    List<String> nmentionList=feed.getMentionList();
	    mongoTemplate.upsert(new Query(Criteria.where("user_id").is(id).and("time").is(itime)), 
			 new Update().set("text", ntext)
			 .set("showLocation", nshowLocation)
			 .set("location", nlocation)
			/* .set("longtitude",nlongtitude)*/
			 .set("nshareArea",nshareArea)
			 .set("mentionList", nmentionList)
			 , Feed.class);
	}

	@Override
	public List<Feed> findFeedsAround(Circle circle) {
		Query query=Query.query(Criteria.where("location").withinSphere(circle));
		List<Feed>feeds=mongoTemplate.find(query,Feed.class);
		return feeds;
	}
	 

		

}
