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
	public List<ReturnFollow> getFollowingInformationById(int userid);
	
	/**
	 * 获取id为userid的所有follower的信息
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowerInformationById(int userid);
	
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
	public ReturnUserInfo getSomeoneInfo(int userId);
}
