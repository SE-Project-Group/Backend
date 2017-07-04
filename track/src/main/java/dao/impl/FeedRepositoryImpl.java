package dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import dao.FeedRepository;
import model.Feed;

public class FeedRepositoryImpl implements FeedRepository{
	 private MongoTemplate mongoTemplate; 
	 
	
	 @Override
	public void insert(Feed feed) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(feed); 
	}

	@Override
	public Feed findOne(String user_id, long time) {
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
	public void removeOne(String user_id, long time) {
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
		String id=feed.getUser_id();
		long itime=feed.getTime();
	    String ntext=feed.getText();   
	    String nshowLocation=feed.getShowLocation();
	    String nlatitude=feed.getLatitude();
	    String nlongtitude=feed.getLongtitude(); 
	    String nshareArea=feed.getShareArea();
	    List<String> nmentionList=feed.getMentionList();
	 mongoTemplate.upsert(new Query(Criteria.where("user_id").is(id).and("time").is(itime)), 
			 new Update().set("text", ntext)
			 .set("showLocation", nshowLocation)
			 .set("latitude", nlatitude)
			 .set("longtitude",nlongtitude)
			 .set("nshareArea",nshareArea)
			 .set("mentionList", nmentionList)
			 , Feed.class);
	}
	 public MongoTemplate getMongoTemplate() {
	        return mongoTemplate;
	    }

	    /**
	     * @param mongoTemplate the mongoTemplate to set
	     */
	    public void setMongoTemplate(MongoTemplate mongoTemplate) {
	        this.mongoTemplate = mongoTemplate;
	    }

}
