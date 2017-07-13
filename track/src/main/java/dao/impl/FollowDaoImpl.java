package dao.impl;

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
	public List<Follow> getMyFollowingById(int id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=?", id);
		/*Follow follow = follows.size() > 0 ? follows.get(0) : null;*/
		return follows;
		
	}
	public List<Follow> getFollowingMeById(int id){
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
		List<Follow> follows = (List<Follow>) getHibernateTemplate().find("from Follow as f where f.userId=? and f.isFriend=ture", id);
		/*Follow follow = follows.size() > 0 ? follows.get(0) : null;*/
		return follows;
	}


}
