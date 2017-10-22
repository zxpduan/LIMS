package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.User;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IRoleDAO;

/**
 * A data access object (DAO) providing persistence and search support for Role entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Role
 * @author MyEclipse Persistence Tools
 */
@Service("roleDAO")
public class RoleDAO extends AbstractDao implements IRoleDAO {
	private static final Log log = LogFactory.getLog(RoleDAO.class);
	// property constants
	public static final String ROLENAME = "rolename";
	public static final String ROLEDESC = "roledesc";

	protected void initDao() {
		// do nothing
	}
	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public String save(Role transientInstance) {			
		log.debug("saving Role instance");
		try {
			List obj_exists=findByProperty("rolename",transientInstance.getRolename());
			if (obj_exists!=null){
				if (obj_exists.size()!=0) return "300";//double
			}
			getHibernateTemplate().save(transientInstance);			
			log.debug("save successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);			
			return "250";//db error
		}
	}

	public void delete(Role persistentInstance) {
		log.debug("deleting Role instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Role findById(java.lang.String id) {
		log.debug("getting Role instance with id: " + id);
		try {
			Role instance = (Role) getHibernateTemplate().get("com.ioes.lims.beans.Role", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Role instance) {
		log.debug("finding Role instance by example");
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
		log.debug("finding Role instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Role as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRolename(Object rolename) {
		return findByProperty(ROLENAME, rolename);
	}

	public List findByRoledesc(Object roledesc) {
		return findByProperty(ROLEDESC, roledesc);
	}

	public List findAll() {
		log.debug("finding all Role instances");
		try {
			String queryString = "from Role";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String merge(Role detachedInstance) {		
		log.debug("merging Role instance");
		try {
			List obj_exists=findByProperty("rolename",detachedInstance.getRolename());
			if (obj_exists!=null){				
				if ((obj_exists.size()==1 && !((Role)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists.size()>1) return "300";//300 is double
			}
			Role result = (Role) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void attachDirty(Role instance) {
		log.debug("attaching dirty Role instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Role instance) {
		log.debug("attaching clean Role instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RoleDAO) ctx.getBean("RoleDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("delete from role where id=?");
			ps2 = conn.prepareStatement("delete from rolefun where roleid=?");
			for (String string : ids) {
				ps.setString(1, string);				
				ps.addBatch();
				ps2.setString(1, string);				
				ps2.addBatch();
			}
			ps.executeBatch();
			ps2.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			try {
				conn.setAutoCommit(true);
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) {
					ps.close();					
				}		
				if (null != ps2) {
					ps2.close();					
				}
				if (null != conn) {					
					conn.close();
				}
				if (null != sess) {					
					sess.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}