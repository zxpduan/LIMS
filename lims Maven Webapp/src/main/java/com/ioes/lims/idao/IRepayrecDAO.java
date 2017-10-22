package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Overdue;
import com.ioes.lims.beans.Repay;
import com.ioes.lims.beans.Repayrec;
import com.ioes.lims.beans.User;
import com.ioes.lims.daoimp.RepayrecDAO;

public interface IRepayrecDAO extends IBaseDAO{
	public void save(Repayrec transientInstance) ;
	
	public String saveAll(List<Repay> repay) ;
	public void saveSingle(Repay repay) ;
	public String savePrepay(Repay repay) ;
	/**
	 * 找到所有当月逾期的合同的逾期信息
	 *
	 * @return 2017年7月12日 by 周喜平
	 */
	public List<LinkedHashMap<String, Object>> findBeOver();
	
	public void delete(Repayrec persistentInstance) ;

	public Repayrec findById(java.lang.String id) ;

	public List findByExample(Repayrec instance) ;

	public List findByProperty(String propertyName, Object value);

	public List findByPid(Object pid);
	

	public List findByAmount(Object amount);

	public List findByRepid(Object repid) ;

	public List findByInstalment(Object instalment);

	public List findAll() ;

	public Repayrec merge(Repayrec detachedInstance);

	public void attachDirty(Repayrec instance);

	public void attachClean(Repayrec instance);	

}
