package com.ioes.lims.daoimp;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Overdue;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IOverdueDAO;

/**
 * A data access object (DAO) providing persistence and search support for Overdue entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Overdue
 * @author MyEclipse Persistence Tools
 */
@Service("overdueDAO")
public class OverdueDAO extends AbstractDao implements IOverdueDAO {
	private static final Log log = LogFactory.getLog(OverdueDAO.class);
	// property constants
	public static final String PID = "pid";
	public static final String PLANID = "planid";
	public static final String AMOUNT = "amount";

	protected void initDao() {
		// do nothing
	}

	public void save(Overdue transientInstance) {
		log.debug("saving Overdue instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Overdue persistentInstance) {
		log.debug("deleting Overdue instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Overdue findById(java.lang.String id) {
		log.debug("getting Overdue instance with id: " + id);
		try {
			Overdue instance = (Overdue) getHibernateTemplate().get("com.ioes.lims.beans.Overdue", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Overdue instance) {
		log.debug("finding Overdue instance by example");
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
		log.debug("finding Overdue instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Overdue as model where model." + propertyName + "= ?";
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

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findAll() {
		log.debug("finding all Overdue instances");
		try {
			String queryString = "from Overdue";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Overdue merge(Overdue detachedInstance) {
		log.debug("merging Overdue instance");
		try {
			Overdue result = (Overdue) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Overdue instance) {
		log.debug("attaching dirty Overdue instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Overdue instance) {
		log.debug("attaching clean Overdue instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static OverdueDAO getFromApplicationContext(ApplicationContext ctx) {
		return (OverdueDAO) ctx.getBean("OverdueDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Overdue findbypidplanid(String pid, String planid) {
		log.debug("find by pid planid");
		try {
			String queryString = "from Overdue as model where model.pid= ? and planid=?";
			List<Overdue> rs= getHibernateTemplate().find(queryString,pid,planid);
			if (rs!=null &&rs.size()>0) return rs.get(0);
			else return null;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}		
	}
	/**
	 * 找到欠款未还完或未还的逾期记录
	 *
	 * @param pid
	 * @param planid
	 * @return 2017年8月12日 by 周喜平
	 */
	public List<Overdue> findArrearsRecs() {
		log.debug("find by pid planid");
		try {
			String queryString = "from Overdue as model where model.payoff-model.amount<0";
			List<Overdue> rs= getHibernateTemplate().find(queryString);
			return rs;		
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}		
	}

	
}