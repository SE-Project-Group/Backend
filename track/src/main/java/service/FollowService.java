package service;

import java.util.List;

import model.Client;
import model.ReturnFollow;
import model.ReturnUserInfo;

public interface FollowService {
	/*
	 * 
	 * Follow
	 */
	/**
	 * 获取id为userid的所有朋友的信息
	 * @param userid
	 * @return
	 */
	public List<Client> getMyFriendInformationById(int userid);
	
	/**
	 * 获取id为userid的所有关注的人的信息
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowingInformationById(int userId,int who);
	
	/**
	 * 获取id为userid的所有follower的信息
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowerInformationById(int userId,int who);
	
	/**
	 * 关注某人
	 * @param userid关注人id
	 * @param followid被关注人id
	 * @return
	 */
	public String followSomeone(int userid,int followid);
	
	/**
	 * 取消关注某人
	 * @param userid关注人id
	 * @param followid被关注人id
	 * @return
	 */
	public String unFollowSomeone(int userid,int followid);
	
	/**
	 * 获取某人的信息
	 * @param userId
	 * @return
	 */
	public ReturnUserInfo getSomeoneInfo(int userId,int who);
	
	/**
	 * 获取两个用户之间的关系
	 * @param userId1
	 * @param userId2
	 * @return
	 */
	public String getRelationship(int userId1,int userId2);
}
