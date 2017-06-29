package service;

import java.util.List;

import model.Client;

public interface AppService {
	
	/*
	 * 
	 * Client
	 */
	
	public void insertClient(Client client);
	public void updateClient(Client client);
	public void deleteClient(Client client);
	public Client getClientByID(int ID);
	public List<Client> getAllClients();
	public boolean clientLogin(String client_name,String password);
	public int checkSignup(String client_name,String password,String phone);	//return 0:ok   1:phone  2:user_name  3:phone&username

	/*
	 * 
	 * Admin
	 */
	
	public boolean managerLogin(String admin_name,String password);
}
