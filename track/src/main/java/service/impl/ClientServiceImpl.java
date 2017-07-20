package service.impl;


import java.util.List;

import dao.ClientDao;
import dao.TokenDao;
import model.Client;
import model.Token;
import service.ClientService;

public class ClientServiceImpl implements ClientService{
private ClientDao clientDao;
	
	private TokenDao tokenDao;
	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	public TokenDao getTokenDao() {
		return tokenDao;
	}

	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}


	@Override
	public void insertClient(Client client) {
		// TODO Auto-generated method stub
		clientDao.insert(client);
		
	}

	@Override
	public void updateClient(Client client) {
		// TODO Auto-generated method stub
		clientDao.update(client);
		
	}

	@Override
	public void deleteClient(Client client) {
		// TODO Auto-generated method stub
		clientDao.delete(client);
		
	}

	@Override
	public Client getClientById(int id) {
		// TODO Auto-generated method stub
		return clientDao.getClientById(id);
	}

	@Override
	public List<Client> getAllClients() {
		// TODO Auto-generated method stub
		return clientDao.getAllClients();
	}

	@Override
	public Token clientLogin(String userName, String password) {
		// TODO Auto-generated method stub
		if(clientDao.checkLogin(userName, password)){
			Client client=clientDao.getClientByUserName(userName);
			return tokenDao.createToken(client.getUserId());
		}
		return null;
	}

	@Override
	public int signup(String clientName, String password, String phone) {
		// TODO Auto-generated method stub
		int flag=clientDao.checkSignup(clientName, password, phone);
		if(flag==0){
			Client client=new Client();
			client.setUserName(clientName);
			client.setPassword(password);
			client.setPhone(phone);
			clientDao.insert(client);
		}
		return flag;
	}

	@Override
	public Client getClientByUserName(String userName) {
		// TODO Auto-generated method stub
		return clientDao.getClientByUserName(userName);
	}

	@Override
	public void logout(int userId) {
		// TODO Auto-generated method stub
		tokenDao.deleteToken(userId);
	}

}
