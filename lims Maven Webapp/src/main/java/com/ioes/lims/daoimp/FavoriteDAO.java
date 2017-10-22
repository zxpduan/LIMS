package com.ioes.lims.daoimp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Favorite;

import com.ioes.lims.beans.Fun;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IFavoriteDAO;

/**
 * A data access object (DAO) providing persistence and search support for Favorite entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Favorite
 * @author MyEclipse Persistence Tools
 */
@Service("favoriteDAO")
public class FavoriteDAO extends AbstractDao implements IFavoriteDAO {
	private static final Log log = LogFactory.getLog(FavoriteDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String FAVCODE = "favcode";
	public static final String FAVVALUE = "favvalue";

	protected void initDao() {
		// do nothing
	}

	public void save(Favorite transientInstance) {
		log.debug("saving Favorite instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Favorite persistentInstance) {
		log.debug("deleting Favorite instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Favorite findById(java.lang.String id) {
		log.debug("getting Favorite instance with id: " + id);
		try {
			Favorite instance = (Favorite) getHibernateTemplate().get("com.ioes.lims.beans.Favorite", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Favorite instance) {
		log.debug("finding Favorite instance by example");
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
		log.debug("finding Favorite instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Favorite as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findByFavcode(Object favcode) {
		return findByProperty(FAVCODE, favcode);
	}

	public List findByFavvalue(Object favvalue) {
		return findByProperty(FAVVALUE, favvalue);
	}

	public List findAll() {
		log.debug("finding all Favorite instances");
		try {
			String queryString = "from Favorite";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Favorite merge(Favorite detachedInstance) {
		log.debug("merging Favorite instance");
		try {
			Favorite result = (Favorite) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Favorite instance) {
		log.debug("attaching dirty Favorite instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Favorite instance) {
		log.debug("attaching clean Favorite instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FavoriteDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FavoriteDAO) ctx.getBean("FavoriteDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}


}