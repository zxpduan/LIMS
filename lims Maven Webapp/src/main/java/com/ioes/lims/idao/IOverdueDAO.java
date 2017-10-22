package com.ioes.lims.idao;

import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Overdue;
import com.ioes.lims.beans.User;

public interface IOverdueDAO extends IBaseDAO{
	public void save(Overdue transientInstance) ;
	public void delete(Overdue persistentInstance) ;

	public Overdue findById(java.lang.String id);

	public List findByExample(Overdue instance);

	public List findByProperty(String propertyName, Object value);
	public List findByPid(Object pid) ;
	public List findByPlanid(Object planid) ;
	public List findByAmount(Object amount) ;

	public List findAll() ;

	public Overdue merge(Overdue detachedInstance);
	public Overdue findbypidplanid(String pid,String planid);

	public void attachDirty(Overdue instance);
	public void attachClean(Overdue instance) ;
	public List<Overdue> findArrearsRecs() ;
}
