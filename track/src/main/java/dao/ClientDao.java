package dao;

import java.util.List;

import model.Client;

public interface ClientDao {

	public void update(Client client);
	public void delete(Client client);
	public void insert(Client client);
	public Client getClientById(int id);
	public List<Client> getAllClients();
	public boolean checkLogin(String clientName,String password);
	public int checkSignup(String clientName,String password,String phone);
	public Client getClientByUserName(String userName);
}
