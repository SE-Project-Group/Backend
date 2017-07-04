package dao;

import java.util.List;

import model.Feed;

public interface FeedRepository {

public void insert(Feed feed); 

public Feed findOne(String user_id,long time);   

public List<Feed> findAll();   

public List<Feed> findByUser_id(String user_id);

public void removeOne(String user_id,long time);   

public void removeAll();   

public void update(Feed feed);   
}
