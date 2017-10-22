package com.ioes.lims.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AbstractDao extends HibernateDaoSupport{
	public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public Object checkNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj;
		}
	}
	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
}
