package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.Pact;

public interface IPactDAO extends IBaseDAO{
	public String save(Pact transientInstance,String indicant) ;
	public String merge(Pact detachedInstance) ;
	public List<LinkedHashMap<String, Object>> getPactInfo(String firstNameChar);
	public List<LinkedHashMap<String, Object>> getPactInfo2(String firstNameChar);
	public void delete(Pact persistentInstance) ;
	public Pact findById(java.lang.String id) ;
	public double findLastMoneyById(java.lang.String id) ;
	public List findByExample(Pact instance) ;
	public int  findMaxSerial(Pact instance) ;
	public List findByProperty(String propertyName, Object value) ;
	public String updateAuditPact(Pact detachedInstance) ;
	
	public List findAll() ;
	
}
