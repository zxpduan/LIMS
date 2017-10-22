package com.ioes.lims.daoimp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Userrole;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IUserroleDAO;

/**
 * A data access object (DAO) providing persistence and search support for Userrole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Userrole
 * @author MyEclipse Persistence Tools
 */
@Service("userroleDAO")
public class UserroleDAO extends AbstractDao implements IUserroleDAO {
	private static final Log log = LogFactory.getLog(UserroleDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String ROLEID = "roleid";

	protected void initDao() {
		// do nothing
	}

	public void save(Userrole transientInstance) {
		log.debug("saving Userrole instance");
		try {
			List<Userrole> list= findByProperty("userid",transientInstance.getUserid());
			for (Userrole userrole : list) {
				delete(userrole);
			}			
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Userrole persistentInstance) {
		log.debug("deleting Userrole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Userrole findById(java.lang.String id) {
		log.debug("getting Userrole instance with id: " + id);
		try {
			Userrole instance = (Userrole) getHibernateTemplate().get("com.ioes.lims.beans.Userrole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Userrole instance) {
		log.debug("finding Userrole instance by example");
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
		log.debug("finding Userrole instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Userrole as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findByRoleid(Object roleid) {
		return findByProperty(ROLEID, roleid);
	}

	public List findAll() {
		log.debug("finding all Userrole instances");
		try {
			String queryString = "from Userrole";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Userrole merge(Userrole detachedInstance) {
		log.debug("merging Userrole instance");
		try {
			Userrole result = (Userrole) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Userrole instance) {
		log.debug("attaching dirty Userrole instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Userrole instance) {
		log.debug("attaching clean Userrole instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserroleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserroleDAO) ctx.getBean("UserroleDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}
}