package dao.impl;

import dao.ClientDao;

import model.Client;

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
	public Client getClientByID(int ID) {
		@SuppressWarnings("unchecked")
		List<Client> clients = (List<Client>) getHibernateTemplate().find("from Client as c where c.user_ID=?", ID);
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
	public boolean checkLogin(String client_name, String password) {
		String hql ="from Client as c where c.user_name=? and c.password=?";
		List<Client> clients=(List<Client>)getHibernateTemplate().find(hql, new String[]{client_name,password});
		if(clients.size()==0)return false;
		return true;
	}

	@Override
	public int checkSignup(String client_name,String password,String phone){
		String hql_username ="from Client as c where c.user_name=?";
		String hql_phone="from Client as c where c.phone=?";
		List<Client> clients_username=(List<Client>)getHibernateTemplate().find(hql_username, new String[]{client_name});
		List<Client> clients_phone=(List<Client>)getHibernateTemplate().find(hql_phone, new String[]{phone});
		if(clients_username.size()==0&&clients_phone.size()==0)return 0;
		else if(clients_username.size()==0&&clients_phone.size()>0)return 1;
		else if(clients_username.size()>0&&clients_phone.size()>0)return 3;
		else return 2;
	}

	@Override
	public Client getClientByUser_name(String user_name) {
		String hql="from Client as c where c.user_name=?";
		List<Client> clients=(List<Client>)getHibernateTemplate().find(hql,new String[]{user_name});
		Client client = clients.size() > 0 ? clients.get(0) : null;
		return client;
	}

}
