package dao.impl;

import java.util.List;

import dao.ClientExtraInfoDao;
import model.ClientExtraInfo;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
public class ClientExtraInfoDaoImpl extends HibernateDaoSupport implements ClientExtraInfoDao{

	@Override
	public void update(ClientExtraInfo clientExtraInfo) {
		getHibernateTemplate().merge(clientExtraInfo);
		
	}

	@Override
	public void delete(ClientExtraInfo clientExtraInfo) {
		getHibernateTemplate().delete(clientExtraInfo);
		
	}

	@Override
	public void insert(ClientExtraInfo clientExtraInfo) {
		getHibernateTemplate().save(clientExtraInfo);
		
	}

	@Override
	public ClientExtraInfo getClientExtraInfoById(int id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ClientExtraInfo> clientclientExtraInfos = (List<ClientExtraInfo>) getHibernateTemplate().find("from ClientExtraInfo as c where c.userId=?", id);
		ClientExtraInfo clientExtraInfo = clientclientExtraInfos .size() > 0 ? clientclientExtraInfos .get(0) : null;
		return clientExtraInfo;
	}

	@Override
	public List<ClientExtraInfo> getClientByHobby(String hobby) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ClientExtraInfo> clientclientExtraInfos = (List<ClientExtraInfo>) getHibernateTemplate().find("from ClientExtraInfo as c where c.hobbyone=? or c.hobbytwo=? or c.hobbythree=?", hobby);
		
		return clientclientExtraInfos;
	}

	@Override
	public List<ClientExtraInfo> getAllClientExtraInfos() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ClientExtraInfo> clientclientExtraInfos = (List<ClientExtraInfo>) getHibernateTemplate().find("from ClientExtraInfo");
	
		return clientclientExtraInfos;
	}

	@Override
	public List<ClientExtraInfo> getClientByCareer(String career) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<ClientExtraInfo> clientclientExtraInfos = (List<ClientExtraInfo>) getHibernateTemplate().find("from ClientExtraInfo as c where c.career=?", career);
		
		return clientclientExtraInfos;
	}

	@Override
	public List<ClientExtraInfo> getClientByEducation(String education) {
		// TODO Auto-generated method stub
@SuppressWarnings("unchecked")
List<ClientExtraInfo> clientclientExtraInfos = (List<ClientExtraInfo>) getHibernateTemplate().find("from ClientExtraInfo as c where c.education=?", education);
		
		return clientclientExtraInfos;
	}

}
