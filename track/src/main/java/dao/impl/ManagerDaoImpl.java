package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ManagerDao;
import model.Manager;

public class ManagerDaoImpl extends HibernateDaoSupport implements ManagerDao{

	@Override
	public boolean checkLogin(String admin_name, String password) {
		String hql ="from Manager as m where m.admin_name=? and m.password=?";
		List<Manager> managers=(List<Manager>)getHibernateTemplate().find(hql, new String[]{admin_name,password});
		if(managers.size()==0)return false;
		return true;
	}

}
