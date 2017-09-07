package service.impl;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.lib.cvsclient.commandLine.command.update;

import dao.ClientDao;
import dao.FollowDao;
import dao.TokenDao;
import model.Client;
import model.ReturnClient;
import model.ReturnFollow;
import model.SignedUrlFactory;
import model.Token;
import service.ClientService;

public class ClientServiceImpl implements ClientService{
	
	private ClientDao clientDao;
	private TokenDao tokenDao;
	private FollowDao followDao;
	
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
	
	public FollowDao getFollowDao() {
		return followDao;
	}

	public void setFollowDao(FollowDao followDao) {
		this.followDao = followDao;
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
			flag=clientDao.getClientByUserName(clientName).getUserId();
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
	
	@Override
	public ReturnClient clientToReturnClient(Client client){
		String user_name=client.getUserName();
		String birthday=client.getBirthday()==null?null:client.getBirthday().toString();
		String gender=client.getGender()==null?null:client.getGender();
		String email=client.getEmail();
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
		String portrait_url=signedUrlFactory.getPortraitUrl(client.getUserId());
		ReturnClient returnClient=new ReturnClient(user_name,gender,birthday,email,portrait_url);
		return returnClient;
	}

	@Override
	public String getPortraitUrl(int userId) {
		return clientDao.getPortraitUrl(userId);
	}

	@Override
	public List<ReturnFollow> searchUser(int userId, String query) {
		List<Client> clients=clientDao.searchClient(query);
		List<ReturnFollow> returnFollows=new ArrayList<ReturnFollow>();
		for(Client client:clients){
			int id=client.getUserId();
			String userName=client.getUserName();
			SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
			String portraitUrl=signedUrlFactory.getPortraitUrl(id);
			String state=followDao.getRelationship(userId, id);
			ReturnFollow returnFollow=new ReturnFollow(userName,portraitUrl,id,state);
			returnFollows.add(returnFollow);
		}
		return returnFollows;
	}

	@Override
	public String getBigPortraitUrl(int userId) {
		Client client=getClientById(userId);
		if(client==null){
			return "failed";
		}
		return clientDao.getBigPortraitUrl(userId);
	}

	@Override
	public boolean changePwd(int userId,String oldPwd, String newPwd) {
		Client client=getClientById(userId);
		if(client.getPassword().equals(oldPwd)){
			client.setPassword(newPwd);
			updateClient(client);
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyPhone(int userId, String phone) {
		Client client=getClientById(userId);
		if(client.getPhone().equals(phone)){
			return true;
		}
		return false;
	}
}
