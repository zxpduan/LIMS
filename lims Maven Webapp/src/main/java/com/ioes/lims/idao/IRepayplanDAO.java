package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.beans.User;
import com.ioes.lims.daoimp.RepayplanDAO;

public interface IRepayplanDAO extends IBaseDAO{
	public void save(Repayplan transientInstance) ;

	public void delete(Repayplan persistentInstance) ;
	public void deleteBypid(String pid) ;

	public Repayplan findById(java.lang.String id) ;
	public Repayplan findByPidPaytime(String pid,int paytime) ;

	public List findByExample(Repayplan instance) ;

	public List findByProperty(String propertyName, Object value);

	public List findByPid(Object pid) ;
	public List findByPaytimes(Object paytimes);
	public List findByPayamount(Object payamount) ;

	public List findAll() ;

	public Repayplan merge(Repayplan detachedInstance) ;

	public void attachDirty(Repayplan instance) ;

	public void attachClean(Repayplan instance) ;	
	
	public void delBatch(String[] ids);
}
