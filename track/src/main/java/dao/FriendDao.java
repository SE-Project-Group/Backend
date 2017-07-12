package dao;

import java.util.List;

import model.Friend;

public interface FriendDao {

	public void update(Friend friend);
	public void delete(Friend friend);
	public void insert(Friend friend);
	public Friend getFriendById(int id);
	public List<Friend> getAllFriends();
}
