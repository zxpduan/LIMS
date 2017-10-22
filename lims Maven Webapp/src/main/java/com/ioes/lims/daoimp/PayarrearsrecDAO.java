package com.ioes.lims.daoimp;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Payarrearsrec;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IPayarrearsrecDAO;

/**
 * A data access object (DAO) providing persistence and search support for Payarrearsrec entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Payarrearsrec
 * @author MyEclipse Persistence Tools
 */
@Service("payarrearsrecDAO")
public class PayarrearsrecDAO extends AbstractDao implements IPayarrearsrecDAO {
	private static final Log log = LogFactory.getLog(PayarrearsrecDAO.class);
	// property constants
	public static final String PID = "pid";
	public static final String PLANID = "planid";
	public static final String REPAYAMOUNT = "repayamount";

	protected void initDao() {
		// do nothing
	}

	public void save(Payarrearsrec transientInstance) {
		log.debug("saving Payarrearsrec instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Payarrearsrec persistentInstance) {
		log.debug("deleting Payarrearsrec instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Payarrearsrec findById(java.lang.String id) {
		log.debug("getting Payarrearsrec instance with id: " + id);
		try {
			Payarrearsrec instance = (Payarrearsrec) getHibernateTemplate().get("com.ioes.lims.beans.Payarrearsrec", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Payarrearsrec instance) {
		log.debug("finding Payarrearsrec instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Payarrearsrec instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Payarrearsrec as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List findByPlanid(Object planid) {
		return findByProperty(PLANID, planid);
	}

	public List findByRepayamount(Object repayamount) {
		return findByProperty(REPAYAMOUNT, repayamount);
	}

	public List findAll() {
		log.debug("finding all Payarrearsrec instances");
		try {
			String queryString = "from Payarrearsrec";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Payarrearsrec merge(Payarrearsrec detachedInstance) {
		log.debug("merging Payarrearsrec instance");
		try {
			Payarrearsrec result = (Payarrearsrec) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Payarrearsrec instance) {
		log.debug("attaching dirty Payarrearsrec instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Payarrearsrec instance) {
		log.debug("attaching clean Payarrearsrec instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PayarrearsrecDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PayarrearsrecDAO) ctx.getBean("PayarrearsrecDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}
}