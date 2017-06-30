package dao;

import java.util.List;

import model.Client;

public interface ClientDao {

	public void update(Client client);
	public void delete(Client client);
	public void insert(Client client);
	public Client getClientByID(int ID);
	public List<Client> getAllClients();
	public boolean checkLogin(String client_name,String password);
	public int checkSignup(String client_name,String password,String phone);
	public Client getClientByUser_name(String user_name);
}
