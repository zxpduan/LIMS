package com.ioes.lims.idao;

import java.util.List;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.User;

public interface IDeptDAO extends IBaseDAO{
	public void save(Dept transientInstance) ;

	public void delete(Dept persistentInstance) ;
	public Dept merge(Dept detachedInstance) ;
	public void update(Dept detachedInstance) ;
	public Dept findById(java.lang.String id) ;
	public List findByExample(Dept instance) ;
	public List findByProperty(String propertyName, Object value);
	public List findByName(Object name); 
	public List findByDeptdesc(Object deptdesc);
	public List findByParent(Object parent) ;
	public List findAll() ;
}
