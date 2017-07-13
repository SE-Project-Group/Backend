package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ManagerDao;
import model.Manager;

public class ManagerDaoImpl extends HibernateDaoSupport implements ManagerDao{

	@Override
	public boolean checkLogin(String adminName, String password) {
		String hql ="from Manager as m where m.adminName=? and m.password=?";
		@SuppressWarnings("unchecked")
		List<Manager> managers=(List<Manager>)getHibernateTemplate().find(hql,adminName,password);
		if(managers.size()==0)return false;
		return true;
	}

}
