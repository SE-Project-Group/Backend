package dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;

import model.Feed;

public interface FeedRepository {

public void insert(Feed feed); 

public Feed findOne(String user_id,Timestamp time);   

public List<Feed> findAll();   

public List<Feed> findByUser_id(String user_id);

public void removeOne(String user_id,Timestamp time);   

public void removeAll();   

public void update(Feed feed);   

public List<Feed> findFeedsAround(Circle circle);
}
