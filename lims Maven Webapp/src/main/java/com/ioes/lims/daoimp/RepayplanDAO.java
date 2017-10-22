package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.action.repayAction;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IRepayplanDAO;

/**
 * A data access object (DAO) providing persistence and search support for Repayplan entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Repayplan
 * @author MyEclipse Persistence Tools
 */
@Service("repayplanDAO")
public class RepayplanDAO extends AbstractDao implements IRepayplanDAO {
	private static final Log log = LogFactory.getLog(RepayplanDAO.class);
	// property constants
	public static final String PID = "pid";
	public static final String PAYTIMES = "paytimes";
	public static final String PAYAMOUNT = "payamount";
	public static final String COST = "cost";
	public static final String INTEREST = "interest";

	protected void initDao() {
		// do nothing
	}

	public void save(Repayplan transientInstance) {
		log.debug("saving Repayplan instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Repayplan persistentInstance) {
		log.debug("deleting Repayplan instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Repayplan findById(java.lang.String id) {
		log.debug("getting Repayplan instance with id: " + id);
		try {
			Repayplan instance = (Repayplan) getHibernateTemplate().get("com.ioes.lims.beans.Repayplan", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Repayplan instance) {
		log.debug("finding Repayplan instance by example");
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
		log.debug("finding Repayplan instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Repayplan as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List findByPaytimes(Object paytimes) {
		return findByProperty(PAYTIMES, paytimes);
	}

	public List findByPayamount(Object payamount) {
		return findByProperty(PAYAMOUNT, payamount);
	}

	public List findByCost(Object cost) {
		return findByProperty(COST, cost);
	}

	public List findByInterest(Object interest) {
		return findByProperty(INTEREST, interest);
	}

	public List findAll() {
		log.debug("finding all Repayplan instances");
		try {
			String queryString = "from Repayplan";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Repayplan merge(Repayplan detachedInstance) {
		log.debug("merging Repayplan instance");
		try {
			Repayplan result = (Repayplan) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Repayplan instance) {
		log.debug("attaching dirty Repayplan instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Repayplan instance) {
		log.debug("attaching clean Repayplan instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RepayplanDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RepayplanDAO) ctx.getBean("RepayplanDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBypid(String pid) {		
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
			ps = conn.prepareStatement("delete from repayplan where pid=?");			
			ps.setString(1, pid);
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
				if (null != sess) {					
					sess.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Repayplan findByPidPaytime(String pid, int paytime) {
		try {
			String queryString = "from Repayplan as model where model.pid=? and model.paytimes=?";
			List<Repayplan> ll= getHibernateTemplate().find(queryString, pid,paytime);
			if (ll!=null&& ll.size()>0) return ll.get(0);
			else return null;
		} catch (RuntimeException re) {
			log.error("findByPidPaytime failed", re);
			throw re;
		}
	}
	
}