package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.Role;

public interface IRoleDAO extends IBaseDAO{
	public String save(Role transientInstance);

	public void delete(Role persistentInstance) ;

	public Role findById(java.lang.String id) ;

	public List findByExample(Role instance) ;

	public List findByProperty(String propertyName, Object value) ;

	public List findByRolename(Object rolename) ;
	public List findByRoledesc(Object roledesc) ;
	public List findAll() ;

	public String merge(Role detachedInstance) ;

	public void attachDirty(Role instance) ;

	public void attachClean(Role instance) ;
	
}
