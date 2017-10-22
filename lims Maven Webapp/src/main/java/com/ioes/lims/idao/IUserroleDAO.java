package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.User;
import com.ioes.lims.beans.Userrole;

public interface IUserroleDAO extends IBaseDAO{
	public void save(Userrole transientInstance) ;
	public void delete(Userrole persistentInstance) ;

	public Userrole findById(java.lang.String id) ;

	public List findByExample(Userrole instance);

	public List findByProperty(String propertyName, Object value);

	public List findByUserid(Object userid);
	public List findByRoleid(Object roleid);

	public List findAll();

	public Userrole merge(Userrole detachedInstance) ;

	public void attachDirty(Userrole instance);

	public void attachClean(Userrole instance) ;
}
