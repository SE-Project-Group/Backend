package dao;

import java.util.List;

import model.Follow;


public interface FollowDao {

	public void update(Follow follow);
	public void delete(Follow follow);
	public void insert(Follow follow);
	public Follow isFollowed(int userId,int followId);
	public List<Follow> getFollowingById(int id);
	public List<Follow> getFollowerById(int id);
	public List<String> getFollowerIdById(int id);
	public List<Follow> getFriendById(int id);
	public List<Follow> getAll();
}
