package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.Rolefun;
import com.ioes.lims.beans.User;

public interface IRolefunDAO extends IBaseDAO{
	public void save(Rolefun transientInstance);

	public void delete(Rolefun persistentInstance) ;
	public Rolefun findById(java.lang.String id) ;

	public List findByExample(Rolefun instance);

	public List findByProperty(String propertyName, Object value) ;

	public List findByRoleid(Object roleid);
	public List findByFunid(Object funid);
	public List findAll();

	public Rolefun merge(Rolefun detachedInstance) ;

	public void attachDirty(Rolefun instance);

	public void attachClean(Rolefun instance) ;
	public void saveRoleFun(String[] funids,String roleid) ;
}
