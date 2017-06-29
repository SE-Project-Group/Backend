package service.impl;

import java.util.List;


import dao.ClientDao;
import dao.ManagerDao;
import model.Client;
import service.AppService;

public class AppServiceImpl implements AppService{
	
	private ClientDao clientDao;
	
	private ManagerDao managerDao;

	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	/*
	 * 
	 * 
	 * client
	 */
	@Override
	public void insertClient(Client client) {
		clientDao.insert(client);
	}

	@Override
	public void updateClient(Client client) {
		clientDao.update(client);
	}

	@Override
	public void deleteClient(Client client) {
		clientDao.delete(client);
	}

	@Override
	public Client getClientByID(int ID) {
		return clientDao.getClientByID(ID);
	}

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}
	
	@Override
	public boolean clientLogin(String client_name, String password) {
		return clientDao.checkLogin(client_name, password);
	}

	@Override
	public int checkSignup(String client_name,String password,String phone) {
		return clientDao.checkSignup(client_name, password, phone);
	}
	
	
	/*
	 * 
	 * Admin
	 */
	
	@Override
	public boolean managerLogin(String admin_name, String password) {
		return managerDao.checkLogin(admin_name, password);
	}

	

	


	

}
