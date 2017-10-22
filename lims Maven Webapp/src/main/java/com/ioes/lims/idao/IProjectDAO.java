package com.ioes.lims.idao;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Project;
import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.User;

public interface IProjectDAO extends IBaseDAO{
	public String save(Project transientInstance);

	public void delete(Project persistentInstance) ;

	public Project findById(java.lang.String id) ;

	public List findByExample(Project instance) ;

	public List findByProperty(String propertyName, Object value) ;

	
	public List findAll() ;

	public String merge(Project detachedInstance) ;

	public void attachDirty(Project instance) ;

	public void attachClean(Project instance) ;
	public List<LinkedHashMap<String, Object>> getProjectInfo(String firstNameChar) ;
	public List<LinkedHashMap<String, Object>> getProjectSerialInfo(String firstNameChar) ;
}
