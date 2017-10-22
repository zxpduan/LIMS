package com.ioes.lims.daoimp;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.User;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IRatioDAO;

/**
 * A data access object (DAO) providing persistence and search support for Ratio entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Ratio
 * @author MyEclipse Persistence Tools
 */
@Service("ratioDAO")
public class RatioDAO extends AbstractDao implements IRatioDAO {
	private static final Log log = LogFactory.getLog(RatioDAO.class);
	// property constants
	public static final String RATIO = "ratio";
	public static final String RATIODESC = "ratiodesc";

	protected void initDao() {
		// do nothing
	}

	public String save(Ratio transientInstance) {
		log.debug("saving Ratio instance");
		try {
			List obj_exists=findByProperty("ratio",transientInstance.getRatio());
			if (obj_exists!=null){
				if (obj_exists.size()!=0) return "300";//300 is account is exists in db
			}
			getHibernateTemplate().save(transientInstance);			
			log.debug("save successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void delete(Ratio persistentInstance) {
		log.debug("deleting Ratio instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Ratio findById(java.lang.String id) {
		log.debug("getting Ratio instance with id: " + id);
		try {
			Ratio instance = (Ratio) getHibernateTemplate().get("com.ioes.lims.beans.Ratio", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Ratio instance) {
		log.debug("finding Ratio instance by example");
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
		log.debug("finding Ratio instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Ratio as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRatio(Object ratio) {
		return findByProperty(RATIO, ratio);
	}

	public List findByRatiodesc(Object ratiodesc) {
		return findByProperty(RATIODESC, ratiodesc);
	}

	public List findAll() {
		log.debug("finding all Ratio instances");
		try {
			String queryString = "from Ratio";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String merge(Ratio detachedInstance) {
		log.debug("merging Ratio instance");
		try {
			List obj_exists=findByProperty("ratio",detachedInstance.getRatio());
			if (obj_exists!=null){				
				
				if ((obj_exists.size()==1 && !((Ratio)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists.size()>1) return "300";//300 is double
			}
			Ratio result = (Ratio) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void attachDirty(Ratio instance) {
		log.debug("attaching dirty Ratio instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Ratio instance) {
		log.debug("attaching clean Ratio instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RatioDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RatioDAO) ctx.getBean("RatioDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LinkedHashMap<String, Object>> getRatioInfo(String firstNameChar) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}