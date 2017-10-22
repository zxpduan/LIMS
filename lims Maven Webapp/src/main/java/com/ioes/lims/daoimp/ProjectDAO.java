package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.ioes.lims.beans.Project;
import com.ioes.lims.beans.Role;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IProjectDAO;

/**
 * A data access object (DAO) providing persistence and search support for Project entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Project
 * @author MyEclipse Persistence Tools
 */
@Service("projectDAO")
public class ProjectDAO extends AbstractDao implements IProjectDAO {
	private static final Log log = LogFactory.getLog(ProjectDAO.class);
	// property constants
	public static final String PNAME = "pname";
	public static final String PCODE = "pcode";
	public static final String PDESC = "pdesc";

	protected void initDao() {
		// do nothing
	}

	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public void delete(Project persistentInstance) {
		log.debug("deleting Project instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Project findById(java.lang.String id) {
		log.debug("getting Project instance with id: " + id);
		try {
			Project instance = (Project) getHibernateTemplate().get("com.ioes.lims.beans.Project", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Project instance) {
		log.debug("finding Project instance by example");
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
		log.debug("finding Project instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Project as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPname(Object pname) {
		return findByProperty(PNAME, pname);
	}

	public List findByPcode(Object pcode) {
		return findByProperty(PCODE, pcode);
	}

	public List findByPdesc(Object pdesc) {
		return findByProperty(PDESC, pdesc);
	}

	public List findAll() {
		log.debug("finding all Project instances");
		try {
			String queryString = "from Project";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	

	public void attachDirty(Project instance) {
		log.debug("attaching dirty Project instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Project instance) {
		log.debug("attaching clean Project instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProjectDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProjectDAO) ctx.getBean("ProjectDAO");
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
			ps = conn.prepareStatement("delete from project where id=?");
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

	@Override
	public String save(Project transientInstance) {		
		log.debug("saving Project instance");
		try {
			Project ex1=new Project();
			Project ex2=new Project();
			ex1.setPcode(transientInstance.getPcode());
			ex2.setPname(transientInstance.getPname());
			List obj_exists1=findByExample(ex1);
			List obj_exists2=findByExample(ex2);
			if (obj_exists1!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists1.size()==1 && !((Project)obj_exists1.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists1.size()>1) return "300";//300 is double
			}
			if (obj_exists2!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists2.size()==1 && !((Project)obj_exists2.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists2.size()>1) return "300";//300 is double
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

	

	@Override
	public String merge(Project transientInstance) {
		
		log.debug("merging Project instance");
		try {
			Project ex1=new Project();
			Project ex2=new Project();
			ex1.setPcode(transientInstance.getPcode());
			ex2.setPname(transientInstance.getPname());
			List obj_exists1=findByExample(ex1);
			List obj_exists2=findByExample(ex2);
			if (obj_exists1!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists1.size()==1 && !((Project)obj_exists1.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists1.size()>1) return "300";//300 is double
			}
			if (obj_exists2!=null){				
				//if (obj_exists.size()!=0 && !((Fun)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) return "300";//300 is double
				if ((obj_exists2.size()==1 && !((Project)obj_exists2.get(0)).getId().equalsIgnoreCase(transientInstance.getId()))||obj_exists2.size()>1) return "300";//300 is double
			}
			Project result = (Project) getHibernateTemplate().merge(transientInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();			
			return "250";//db error
		}
	}
	/**
	 * 模糊查找，根据案场的拼音字母查询名字
	 *
	 * @param firstNameChar
	 * @return 2016年8月21日 by 周喜平
	 */
	public List<LinkedHashMap<String, Object>> getProjectInfo(String firstNameChar) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select * from project where firstPinyin(`pname`) =? or pinyin(`pname`) like ? or pcode like ?";
		
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
					
					sqlstring = "select * from project limit 0,20";
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
	public List<LinkedHashMap<String, Object>> getProjectSerialInfo(String firstNameChar) {
		// TODO Auto-generated method stub
		return null;
	}
}