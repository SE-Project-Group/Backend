package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.FollowDao;
import model.Client;
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

	@Override
	public boolean isFriend(int userId, int friendId) {
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.followId=? and f.isFriend=?", userId,friendId,1);
		if(follows.size()>0){
			return true;
		}
		return false;
	}


}
