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
	 * �����û�
	 * @param client
	 */
	public void insertClient(Client client);
	
	/**
	 * �����û�
	 * @param client
	 */
	public void updateClient(Client client);
	
	/**
	 * ɾ���û�
	 * @param client
	 */
	public void deleteClient(Client client);
	/**
	 * ͨ��id��ȡ�û�
	 * @param id
	 * @return
	 */
	public Client getClientById(int id);
	
	/**
	 * ��ȡȫ���û�
	 * @return
	 */
	public List<Client> getAllClients();
	
	/**
	 * �û���¼
	 * @param userName
	 * @param password
	 * @return
	 */
	public Token clientLogin(String userName,String password);
	
	/**
	 * �û�ע��
	 * @param clientName
	 * @param password
	 * @param phone
	 * @return
	 */
	public int signup(String clientName,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username
	
	/**
	 * ͨ��username�����û�
	 * @param userName
	 * @return
	 */
	public Client getClientByUserName(String userName);
	
	
	/**
	 * �û��ǳ�
	 * @param userId
	 */
	public void logout(int userId);
	
	/**
	 * clientתReturnClient
	 * @param client
	 * @return
	 */
	public ReturnClient clientToReturnClient(Client client);
	
	/**
	 * ��ȡͷ��url
	 * @param userId
	 * @return
	 */
	public String getPortraitUrl(int userId);
}
