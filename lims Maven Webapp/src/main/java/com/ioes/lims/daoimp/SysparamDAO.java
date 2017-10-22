package com.ioes.lims.daoimp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Sysparam;
import com.ioes.lims.beans.User;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.ISysparamDAO;

/**
 * A data access object (DAO) providing persistence and search support for Sysparam entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Sysparam
 * @author MyEclipse Persistence Tools
 */
@Service("sysparamDAO")
public class SysparamDAO extends AbstractDao implements ISysparamDAO {
	private static final Log log = LogFactory.getLog(SysparamDAO.class);
	// property constants
	public static final String OVERDUEDAY = "overdueday";

	protected void initDao() {
		// do nothing
	}

	public void save(Sysparam transientInstance) {
		log.debug("saving Sysparam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Sysparam persistentInstance) {
		log.debug("deleting Sysparam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Sysparam findById(java.lang.String id) {
		log.debug("getting Sysparam instance with id: " + id);
		try {
			Sysparam instance = (Sysparam) getHibernateTemplate().get("com.ioes.lims.beans.Sysparam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Sysparam instance) {
		log.debug("finding Sysparam instance by example");
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
		log.debug("finding Sysparam instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Sysparam as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOverdueday(Object overdueday) {
		return findByProperty(OVERDUEDAY, overdueday);
	}
	public Sysparam findOnlyOne() {
		try {
			String queryString = "from Sysparam";
			List<Sysparam> re=getHibernateTemplate().find(queryString);
			if (re==null||re.size()==0){
				return null;
			}else{
				
			}
			return re.get(0); 
		} catch (RuntimeException re) {
			log.error("find Only One failed", re);
			throw re;
		}
	}
	public List findAll() {
		log.debug("finding all Sysparam instances");
		try {
			String queryString = "from Sysparam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String merge(Sysparam detachedInstance) {
		log.debug("merging Sysparam instance");
		try {			
			Sysparam result = (Sysparam) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void attachDirty(Sysparam instance) {
		log.debug("attaching dirty Sysparam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Sysparam instance) {
		log.debug("attaching clean Sysparam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SysparamDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SysparamDAO) ctx.getBean("SysparamDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}
}