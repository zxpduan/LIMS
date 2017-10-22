package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.Fun;
import com.ioes.lims.beans.Role;

public interface IFunDAO extends IBaseDAO{
	
	public String save(Fun transientInstance) ;
	public String merge(Fun detachedInstance) ;
	public void delete(Fun persistentInstance);

	public Fun findById(java.lang.String id) ;

	public List findByExample(Fun instance);

	public List findByProperty(String propertyName, Object value) ;

	public List findByFunname(Object funname) ;
	public List findByFuncode(Object funcode) ;
	public List findByFunrul(Object funrul);

	public List findByFundesc(Object fundesc);

	public List findAll();
}
