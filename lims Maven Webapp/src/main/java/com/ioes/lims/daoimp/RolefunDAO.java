package com.ioes.lims.daoimp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Rolefun;
import com.ioes.lims.beans.Userrole;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IRolefunDAO;
import com.sias.util.SessionUtil;

/**
 * A data access object (DAO) providing persistence and search support for Rolefun entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Rolefun
 * @author MyEclipse Persistence Tools
 */
@Service("rolefunDAO")
public class RolefunDAO extends AbstractDao implements IRolefunDAO {
	private static final Log log = LogFactory.getLog(RolefunDAO.class);
	// property constants
	public static final String ROLEID = "roleid";
	public static final String FUNID = "funid";

	protected void initDao() {
		// do nothing
	}

	public void save(Rolefun transientInstance) {
		log.debug("saving Rolefun instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Rolefun persistentInstance) {
		log.debug("deleting Rolefun instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Rolefun findById(java.lang.String id) {
		log.debug("getting Rolefun instance with id: " + id);
		try {
			Rolefun instance = (Rolefun) getHibernateTemplate().get("com.ioes.lims.beans.Rolefun", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Rolefun instance) {
		log.debug("finding Rolefun instance by example");
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
		log.debug("finding Rolefun instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Rolefun as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRoleid(Object roleid) {
		return findByProperty(ROLEID, roleid);
	}

	public List findByFunid(Object funid) {
		return findByProperty(FUNID, funid);
	}

	public List findAll() {
		log.debug("finding all Rolefun instances");
		try {
			String queryString = "from Rolefun";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Rolefun merge(Rolefun detachedInstance) {
		log.debug("merging Rolefun instance");
		try {
			Rolefun result = (Rolefun) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Rolefun instance) {
		log.debug("attaching dirty Rolefun instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Rolefun instance) {
		log.debug("attaching clean Rolefun instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RolefunDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RolefunDAO) ctx.getBean("RolefunDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveRoleFun(String[] funids, String roleid) {
		log.debug("save role fun ..");
		try {
			List<Rolefun> list= findByProperty("roleid",roleid);
			for (Rolefun rolefun : list) {
				delete(rolefun);
			}	
			
			for (String string : funids) {
				Rolefun rf=new Rolefun();
				rf.setId(SessionUtil.getSessionString());
				rf.setRoleid(roleid);
				rf.setFunid(string);
				save(rf);
			}			
			log.debug("save role fun successful");
		} catch (RuntimeException re) {
			log.error("save role fun failed", re);
			re.printStackTrace();
			throw re;
		}
		
	}
}