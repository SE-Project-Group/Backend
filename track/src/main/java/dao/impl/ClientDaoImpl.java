package dao.impl;

import dao.ClientDao;

import model.Client;
import model.SignedUrlFactory;

import java.util.List;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ClientDaoImpl extends HibernateDaoSupport implements ClientDao{

	@Override
	public void update(Client client) {
		getHibernateTemplate().merge(client);
	}

	@Override
	public void delete(Client client) {
		getHibernateTemplate().delete(client);
	}

	@Override
	public void insert(Client client) {
		getHibernateTemplate().save(client);
	}

	@Override
	public Client getClientById(int id) {
		@SuppressWarnings("unchecked")
		List<Client> clients = (List<Client>) getHibernateTemplate().find("from Client as c where c.userId=?", id);
		Client client = clients.size() > 0 ? clients.get(0) : null;
		return client;
	}

	@Override
	public List<Client> getAllClients() {
		@SuppressWarnings("unchecked")
		List<Client> clients = (List<Client>) getHibernateTemplate().find("from Client");
		return clients;
	}

	@Override
	public boolean checkLogin(String clientName, String password) {
		String hql ="from Client as c where c.userName=? and c.password=?";
		@SuppressWarnings("unchecked")
		List<Client> clients=(List<Client>)getHibernateTemplate().find(hql,clientName,password);
		if(clients.size()==0)return false;
		return true;
	}

	@Override
	public int checkSignup(String clientName,String password,String phone){
		String hqlUsername ="from Client as c where c.userName=?";
		String hqlPhone="from Client as c where c.phone=?";
		@SuppressWarnings("unchecked")
		List<Client> clientsUsername=(List<Client>)getHibernateTemplate().find(hqlUsername, clientName);
		@SuppressWarnings("unchecked")
		List<Client> clientsPhone=(List<Client>)getHibernateTemplate().find(hqlPhone,phone);
		if(clientsUsername.size()==0&&clientsPhone.size()==0)return 0;
		else if(clientsUsername.size()==0&&clientsPhone.size()>0)return -1;
		else if(clientsUsername.size()>0&&clientsPhone.size()>0)return -3;
		else return -2;
	}

	@Override
	public Client getClientByUserName(String userName) {
		String hql="from Client as c where c.userName=?";
		@SuppressWarnings("unchecked")
		List<Client> clients=(List<Client>)getHibernateTemplate().find(hql,new String[]{userName});
		Client client = clients.size() > 0 ? clients.get(0) : null;
		return client;
	}

	@Override
	public String getPortraitUrl(int userId) {
		SignedUrlFactory signedUrlFactory =new SignedUrlFactory();
		return signedUrlFactory.getPortraitUrl(userId);
	}

	@Override
	public List<Client> searchClient(String query) {
		String hql1="from Client as c where c.userName like ?";
		String hql2="from Client as c where c.userName=?";
		List<Client> clients1=(List<Client>)getHibernateTemplate().find(hql2,query);
		List<Client> clients2=(List<Client>)getHibernateTemplate().find(hql1,"%"+query+"%");
		if(clients1.size()>0){
			for(int i=0;i<clients2.size();i++){
				Client client=clients2.get(i);
				if(query.equals(client.getUserName())){
					clients2.remove(i);
					break;
				}
			}
			clients2.add(0, clients1.get(0));
		}
		return clients2;
	}

	@Override
	public String getBigPortraitUrl(int userId) {
		SignedUrlFactory signedUrlFactory=new SignedUrlFactory();
		return signedUrlFactory.getBigPortraitUrl(userId);
	}

}
