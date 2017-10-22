package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IDeptDAO;

/**
 * A data access object (DAO) providing persistence and search support for Dept entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Dept
 * @author MyEclipse Persistence Tools
 */
@Service("deptDAO")
public class DeptDAO extends AbstractDao implements IDeptDAO {
	private static final Log log = LogFactory.getLog(DeptDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DEPTDESC = "deptdesc";
	public static final String PARENT = "parent";

	protected void initDao() {
		// do nothing
	}
	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public void save(Dept transientInstance) {
		log.debug("saving Dept instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Dept persistentInstance) {
		log.debug("deleting Dept instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Dept findById(java.lang.String id) {
		log.debug("getting Dept instance with id: " + id);
		try {
			Dept instance = (Dept) getHibernateTemplate().get("com.ioes.lims.beans.Dept", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Dept instance) {
		log.debug("finding Dept instance by example");
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
		log.debug("finding Dept instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Dept as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByDeptdesc(Object deptdesc) {
		return findByProperty(DEPTDESC, deptdesc);
	}

	public List findByParent(Object parent) {
		return findByProperty(PARENT, parent);
	}

	public List findAll() {
		log.debug("finding all Dept instances");
		try {
			String queryString = "from Dept";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dept merge(Dept detachedInstance) {
		log.debug("merging Dept instance");
		try {
			Dept result = (Dept) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dept instance) {
		log.debug("attaching dirty Dept instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dept instance) {
		log.debug("attaching clean Dept instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DeptDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DeptDAO) ctx.getBean("DeptDAO");
	}

	@Override
	public void delBatch(String[] ids) {

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;//for delete cascade in user 
		ResultSet rs = null;
		try {
			conn = this.getHibernateTemplate().getSessionFactory().openSession().connection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement("delete from dept where (id=? or parent=?) and id<>'0'");
			ps2 = conn.prepareStatement("delete from user where dept=?");
			
			for (String string : ids) {
				ps1.setString(1, string);
				ps2.setString(1, string);
				ps1.setString(2, string);				
				ps1.addBatch();
				ps2.addBatch();
			}
			ps1.executeBatch();
			ps2.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			try {				
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {			
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {

				if (null != ps1) ps1.close();
				if (null != ps2) ps2.close();
				if (null != conn) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Dept detachedInstance) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getHibernateTemplate().getSessionFactory().openSession().connection();
			ps = conn.prepareStatement("update dept set name=?,deptdesc=?,parent=? where id=?");
			
			ps.setString(1, detachedInstance.getName());
			ps.setString(2, detachedInstance.getDeptdesc());
			ps.setString(3, detachedInstance.getParent());
			ps.setString(4, detachedInstance.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != ps) {
					ps.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}
}