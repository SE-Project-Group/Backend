package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.FriendDao;
import model.Friend;

public class FriendDaoImpl extends HibernateDaoSupport implements FriendDao{

	@Override
	public void update(Friend friend) {
		getHibernateTemplate().merge(friend);
	}

	@Override
	public void delete(Friend friend) {
		getHibernateTemplate().delete(friend);
		
	}

	@Override
	public void insert(Friend friend) {
		getHibernateTemplate().save(friend);
		
	}

	@Override
	public Friend getFriendById(int id) {
		@SuppressWarnings("unchecked")
		List<Friend> friends = (List<Friend>) getHibernateTemplate().find("from Friend as f where f.userId=?", id);
		Friend friend = friends.size() > 0 ? friends.get(0) : null;
		return friend;
	}

	@Override
	public List<Friend> getAllFriends() {
		@SuppressWarnings("unchecked")
		List<Friend> friends = (List<Friend>) getHibernateTemplate().find("from Friend");
		return friends;
	}


}
