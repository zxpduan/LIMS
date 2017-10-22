package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import com.ioes.lims.beans.Fun;
import com.ioes.lims.beans.Role;
import com.ioes.lims.idao.IFunDAO;

/**
 * A data access object (DAO) providing persistence and search support for Fun entities. Transaction control of the save(), update() and delete() operations can
 * directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods provides
 * additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Fun
 * @author MyEclipse Persistence Tools
 */
@Service("funDAO")
public class FunDAO extends HibernateDaoSupport implements IFunDAO{
	private static final Log log = LogFactory.getLog(FunDAO.class);
	// property constants
	public static final String FUNNAME = "funname";
	public static final String FUNCODE = "funcode";
	public static final String FUNRUL = "funrul";
	public static final String FUNDESC = "fundesc";

	protected void initDao() {
		// do nothing
	}
	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public String save(Fun transientInstance) {		
		log.debug("saving Fun instance");
		try {
			Fun ex1=new Fun();
			Fun ex2=new Fun();
			ex1.setFuncode(transientInstance.getFuncode());
			ex2.setFunname(transientInstance.getFunname());
			List obj_exists1=findByExample(ex1);
			List obj_exists2=findByExample(ex2);
			if (obj_exists1!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists1.size()==1 && !((Fun)obj_exists1.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists1.size()>1) return "300";//300 is double
			}
			if (obj_exists2!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists2.size()==1 && !((Fun)obj_exists2.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists2.size()>1) return "300";//300 is double
			}
			getHibernateTemplate().save(transientInstance);			
			log.debug("save successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);			
			return "250";//db error
		}
	}

	public void delete(Fun persistentInstance) {
		log.debug("deleting Fun instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fun findById(java.lang.String id) {
		log.debug("getting Fun instance with id: " + id);
		try {
			Fun instance = (Fun) getHibernateTemplate().get("com.ioes.lims.beans.Fun", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Fun instance) {
		log.debug("finding Fun instance by example");
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
		log.debug("finding Fun instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Fun as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFunname(Object funname) {
		return findByProperty(FUNNAME, funname);
	}

	public List findByFuncode(Object funcode) {
		return findByProperty(FUNCODE, funcode);
	}

	public List findByFunrul(Object funrul) {
		return findByProperty(FUNRUL, funrul);
	}

	public List findByFundesc(Object fundesc) {
		return findByProperty(FUNDESC, fundesc);
	}

	public List findAll() {
		log.debug("finding all Fun instances");
		try {
			String queryString = "from Fun";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String merge(Fun detachedInstance) {
		try {
			Fun ex1=new Fun();
			Fun ex2=new Fun();
			ex1.setFuncode(detachedInstance.getFuncode());
			ex2.setFunname(detachedInstance.getFunname());
			List obj_exists1=findByExample(ex1);
			List obj_exists2=findByExample(ex2);
			if (obj_exists1!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists1.size()==1 && !((Fun)obj_exists1.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists1.size()>1) return "300";//300 is double
			}
			if (obj_exists2!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists2.size()==1 && !((Fun)obj_exists2.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists2.size()>1) return "300";//300 is double
			}
			Fun result = (Fun) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void attachDirty(Fun instance) {
		log.debug("attaching dirty Fun instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fun instance) {
		log.debug("attaching clean Fun instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FunDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FunDAO) ctx.getBean("FunDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
			ps = conn.prepareStatement("delete from fun where id=?");
			for (String string : ids) {
				ps.setString(1, string);				
				ps.addBatch();
			}
			ps.executeBatch();

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
}