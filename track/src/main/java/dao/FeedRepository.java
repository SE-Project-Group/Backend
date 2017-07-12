package dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.geo.Circle;
import model.Feed;

public interface FeedRepository {

public void insert(Feed feed); 

public Feed findOne(String _id);   

public List<Feed> findAll();   

public List<Feed> findByUser_id(int user_id);

public void removeOne(String _id);   

public void removeAll();   

public void update(Feed feed);   

public List<Feed> findFeedsAround(Circle circle);

public List<Feed> findPublicFeedsByTime(Timestamp time);
}
