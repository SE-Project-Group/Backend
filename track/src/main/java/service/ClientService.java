package service;

import java.util.List;

import model.Client;
import model.ReturnClient;
import model.Token;

public interface ClientService {
	/*
	 * 
	 * Client
	 */
	
	/**
	 * 插入用户
	 * @param client
	 */
	public void insertClient(Client client);
	
	/**
	 * 更新用户
	 * @param client
	 */
	public void updateClient(Client client);
	
	/**
	 * 删除用户
	 * @param client
	 */
	public void deleteClient(Client client);
	/**
	 * 通过id获取用户
	 * @param id
	 * @return
	 */
	public Client getClientById(int id);
	
	/**
	 * 获取全部用户
	 * @return
	 */
	public List<Client> getAllClients();
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public Token clientLogin(String userName,String password);
	
	/**
	 * 用户注册
	 * @param clientName
	 * @param password
	 * @param phone
	 * @return
	 */
	public int signup(String clientName,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	
	/**
	 * 通过username查找用户
	 * @param userName
	 * @return
	 */
	public Client getClientByUserName(String userName);
	
	
	/**
	 * 用户登出
	 * @param userId
	 */
	public void logout(int userId);
	
	/**
	 * client转ReturnClient
	 * @param client
	 * @return
	 */
	public ReturnClient clientToReturnClient(Client client);
	
	/**
	 * 获取头像url
	 * @param userId
	 * @return
	 */
	public String getPortraitUrl(int userId);
}
