package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.User;

public interface IUserDAO extends IBaseDAO{
	public String save(User transientInstance) ;
	public String merge(User detachedInstance) ;
	public List<LinkedHashMap<String, Object>> getUserInfo(String firstNameChar);
	public void delete(User persistentInstance) ;
	
	public User findById(java.lang.String id) ;
	public User findByAccountPw(String account,String pw) ;

	public List findByExample(User instance) ;

	public List findByProperty(String propertyName, Object value) ;

	public List findByAccount(Object account);

	public List findByUsername(Object username);

	public List findByPw(Object pw) ;

	public List findByDept(Object dept);

	public List findAll() ;
	
}
