package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.FollowDao;
import model.Follow;



public class FollowDaoImpl extends HibernateDaoSupport implements FollowDao{

	@Override
	public void update(Follow follow) {
		// TODO Auto-generated method stub
		getHibernateTemplate().merge(follow);
	}

	@Override
	public void delete(Follow follow) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(follow);
	}

	@Override
	public void insert(Follow follow) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(follow);
	}

	@Override
	public List<Follow> getFollowingById(int id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=?", id);
		/*Follow follow = follows.size() > 0 ? follows.get(0) : null;*/
		return follows;
		
	}
	public List<Follow> getFollowerById(int id){
		// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.followId=?", id);
				/*Follow follow = follows.size() > 0 ? follows.get(0) : null;*/
				return follows;
	}
	@Override
	public List<Follow> getAll() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow");
		return follows;
	}

	@Override
	public List<Follow> getFriendById(int id) {
		@SuppressWarnings("unchecked")
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.isFriend=1", id);
		/*Follow follow = follows.size() > 0 ? follows.get(0) : null;*/
		return follows;
	}

	@Override
	public Follow isFollowed(int userId, int followId) {
		@SuppressWarnings("unchecked")
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.followId=?",userId,followId);
	
		Follow follow = follows.size() > 0 ? follows.get(0) : null;
		return follow;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFollowerIdById(int id) {
		// TODO Auto-generated method stub
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.followId=?", id);
		List<String>res=new ArrayList<String>();
		for(int i=0;i<follows.size();i++)
		{Follow follow=follows.get(i);
		String rid=String.valueOf(follow.getUserId());	
		res.add(rid);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isFriend(int userId, int friendId) {
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.followId=? and f.isFriend=?", userId,friendId,1);
		if(follows.size()>0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRelationship(int userId1, int userId2) {
		if(userId1==0) return "stranger";
		if(userId1==userId2) return "";
		List<Follow> follows1 = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.followId=?", userId1,userId2);
		List<Follow> follows2 = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.followId=?", userId2,userId1);
		int size1=follows1.size();
		int size2=follows2.size();
		if(size1>0&&size2>0){
			return "friend";
		}
		else if(size1>0&&size2==0){
			return "following";
		}
		else if(size1==0&&size2>0){
			return "follower";
		}
		else return "stranger";
	}


}
