package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.ClosingUIBean;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.User;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IUserDAO;

/**
 * A data access object (DAO) providing persistence and search support for User entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.User
 * @author MyEclipse Persistence Tools
 */
@Service("userDAO")
public class UserDAO extends AbstractDao implements IUserDAO{
	private static final Log log = LogFactory.getLog(UserDAO.class);
	// property constants
	public static final String ACCOUNT = "account";
	public static final String USERNAME = "username";
	public static final String PW = "pw";
	public static final String DEPT = "dept";

	protected void initDao() {
		// do nothing
	}
	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public String save(User transientInstance) {
		log.debug("saving User instance");
		try {
			List obj_exists=findByProperty("account",transientInstance.getAccount());
			if (obj_exists!=null){
				if (obj_exists.size()!=0) return "300";//300 is account is exists in db
			}
			getHibernateTemplate().save(transientInstance);			
			log.debug("save successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);			
			return "250";//db error
		}
	}
	
	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {			
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.String id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get("com.ioes.lims.beans.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
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
		log.debug("finding User instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from User as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByPw(Object pw) {
		return findByProperty(PW, pw);
	}

	public List findByDept(Object dept) {
		return findByProperty(DEPT, dept);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			List obj_exists=findByProperty("account",detachedInstance.getAccount());
			if (obj_exists!=null){				
				
				if ((obj_exists.size()==1 && !((User)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists.size()>1) return "300";//300 is double
			}
			User result = (User) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}
	@Override
	public void delBatch(String[] ids) {
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("delete from user where id=?");
			ps2 = conn.prepareStatement("delete from userrole where userid=?");
			for (String string : ids) {
				ps.setString(1, string);
				ps2.setString(1, string);
				ps.addBatch();
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
	/**
	 * 模糊查找，根据姓名的拼音字母查询名字
	 *
	 * @param firstNameChar
	 * @return 2016年8月21日 by 周喜平
	 */
	public List<LinkedHashMap<String, Object>> getUserInfo(String firstNameChar) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select * from user where firstPinyin(`username`) =? or pinyin(`username`) like ? or account like ?";
		
		List<LinkedHashMap<String, Object>> searchRs = new ArrayList<LinkedHashMap<String, Object>>();
		ResultSetMetaData rsmd = null;
		String key = null;
		Object value = null;
		LinkedHashMap map = null;
		ResultSet rSet = null;
		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
				if (firstNameChar!=null && !firstNameChar.trim().equals("")){
					
					ps = conn.prepareStatement(sqlstring);
					ps.setString(1, firstNameChar);
					ps.setString(2, "%"+firstNameChar+"%");
					ps.setString(3, "%"+firstNameChar+"%");
				}else{					
					
					sqlstring = "select * from user limit 0,20";
					ps = conn.prepareStatement(sqlstring);
				}
				rSet = ps.executeQuery();
				rsmd = rSet.getMetaData();	
				
				int count = 0;
				
				while (rSet.next()) {
					count++;
					map = new LinkedHashMap();
					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						key = rsmd.getColumnName(i + 1);
						value = checkNull(rSet.getObject(key));
						// value = this.rs.getObject(key);
						map.put(key.trim().toUpperCase(), value);
						map.put("RN", count);
					}
					searchRs.add(map);					
				}
		
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error("error 4:", e);
			searchRs = null;
		} finally {
			try {
				if (null != rSet) {
					rSet.close();					
				}
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
		return searchRs;
	}
	@Override
	public User findByAccountPw(String account, String pw) {		
		try {
			String queryString = "from User as model where model.account=? and pw=?";
			List<User> lu= getHibernateTemplate().find(queryString, account,pw);
			if (lu!=null&&lu.size()>0) return lu.get(0);
			else return null;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}