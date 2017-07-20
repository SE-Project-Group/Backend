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
	 * ��ȡidΪuserid���������ѵ���Ϣ
	 * @param userid
	 * @return
	 */
	public List<Client> getMyFriendInformationById(int userid);
	
	/**
	 * ��ȡidΪuserid�����й�ע���˵���Ϣ
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowingInformationById(int userid);
	
	/**
	 * ��ȡidΪuserid������follower����Ϣ
	 * @param userid
	 * @return
	 */
	public List<ReturnFollow> getFollowerInformationById(int userid);
	
	/**
	 * ��עĳ��
	 * @param userid��ע��id
	 * @param followid����ע��id
	 * @return
	 */
	public String followSomeone(int userid,int followid);
	
	/**
	 * ȡ����עĳ��
	 * @param userid��ע��id
	 * @param followid����ע��id
	 * @return
	 */
	public String unFollowSomeone(int userid,int followid);
	
	/**
	 * ��ȡĳ�˵���Ϣ
	 * @param userId
	 * @return
	 */
	public ReturnUserInfo getSomeoneInfo(int userId);
}
