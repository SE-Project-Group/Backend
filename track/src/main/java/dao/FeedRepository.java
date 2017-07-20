package dao;



import java.sql.Date;

import java.sql.Timestamp;

import java.util.List;

import org.springframework.data.geo.Circle;
import model.Feed;

public interface FeedRepository {

/*
 * �����¶�̬
 */
public void insert(Feed feed); 

/*
 * ����_id������̬
 */
public Feed findOne(String _id);   

/*
 * ȡ�����ж�̬
 */
public List<Feed> findAll();   

/*
 * ����userId������̬��ȡ�����û����ж�̬
 */
public List<Feed> findByUserId(int userId);

/*
 * ����userId����ĳ�����й�����̬
 */
public List<Feed> findPublicFeedsByUserId(int userId);

/*
 * ����userId����ĳ���������ѿɼ���̬
 */
public List<Feed> findFriendFeedsByUserId(int userId);

/*
 * ����_idɾ����̬
 */
public void removeOne(String _id);   

/*
 * ɾ�����ж�̬
 */
public void removeAll();   

/*
 * ���¶�̬
 */
public void update(Feed feed);   


/*
 * ȡ��circle�ڶ�̬
 */
public List<Feed> findFeedsAround(Circle circle);
/*
 * ȡ��ͬһ�û�circle�ڶ�̬
 */
public List<Feed> findFeedsAroundSpecUser(Circle circle,int userId);
/*
 * ����time֮��ķ���ΧΪpublic��20��feed
 */
public List<Feed> findPublicFeedsAfterTime(Timestamp time);
/*
 * ����time֮ǰ�ķ���ΧΪpublic��20��feed
 */
public List<Feed> findPublicFeedsBeforeTime(Timestamp time);

/*
 * ����time֮������е�feed
 */
public List<Feed> findFeedsByTime(Timestamp time);

/*
 * ��������Ϊdate�����е�feed
 */
public List<Feed> getTodayFeedList(Date date);

}
