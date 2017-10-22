package com.ioes.lims.idao;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Overdue;
import com.ioes.lims.beans.Payarrearsrec;
import com.ioes.lims.beans.User;
import com.ioes.lims.daoimp.PayarrearsrecDAO;

public interface IPayarrearsrecDAO extends IBaseDAO{
	public void save(Payarrearsrec transientInstance) ;

	public void delete(Payarrearsrec persistentInstance) ;

	public Payarrearsrec findById(java.lang.String id) ;

	public List findByExample(Payarrearsrec instance) ;

	public List findByProperty(String propertyName, Object value);

	public List findByPid(Object pid) ;
	public List findByPlanid(Object planid) ;
	public List findByRepayamount(Object repayamount);
	public List findAll() ;

	public Payarrearsrec merge(Payarrearsrec detachedInstance);

	public void attachDirty(Payarrearsrec instance);

	public void attachClean(Payarrearsrec instance);

	
}
