package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.Ratio;

public interface IRatioDAO extends IBaseDAO{
	public String save(Ratio transientInstance) ;
	public String merge(Ratio detachedInstance) ;
	public List<LinkedHashMap<String, Object>> getRatioInfo(String firstNameChar);
	public void delete(Ratio persistentInstance) ;
	
	public Ratio findById(java.lang.String id) ;

	public List findByExample(Ratio instance) ;

	public List findByProperty(String propertyName, Object value) ;

	

	public List findAll() ;
	
}
