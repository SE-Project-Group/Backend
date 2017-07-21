package dao;



import java.sql.Date;

import java.sql.Timestamp;

import java.util.List;

import org.springframework.data.geo.Circle;
import model.Feed;

public interface FeedRepository {

/*
 * 插入新动态
 */
public void insert(Feed feed); 

/*
 * 根据_id搜索动态
 */
public Feed findOne(String _id);   

/*
 * 取出所有动态
 */
public List<Feed> findAll();   

/*
 * 根据userId搜索动态，取出该用户所有动态
 */
public List<Feed> findByUserId(int userId);

/*
 * 根据userId查找某人所有公开动态
 */
public List<Feed> findPublicFeedsByUserId(int userId);

/*
 * 根据userId查找某人所有朋友可见动态
 */
public List<Feed> findFriendFeedsByUserId(int userId);

/*
 * 根据_id删除动态
 */
public void removeOne(String _id);   

/*
 * 删除所有动态
 */
public void removeAll();   

/*
 * 更新动态
 */
public void update(Feed feed);   


/*
 * 取出circle内动态
 */
public List<Feed> findFeedsAround(Circle circle);
/*
 * 取出同一用户circle内动态
 */
public List<Feed> findFeedsAroundSpecUser(Circle circle,int userId);
/*
 * 返回time之后的分享范围为public的20条feed
 */
public List<Feed> findPublicFeedsAfterTime(Timestamp time);
/*
 * 返回time之前的分享范围为public的20条feed
 */
public List<Feed> findPublicFeedsBeforeTime(Timestamp time);

/*
 * 返回time之后的所有的feed
 */
public List<Feed> findFeedsByTime(Timestamp time);

/*
 * 返回日期为date的所有的feed
 */
public List<Feed> getTodayFeedList(Date date);

}
