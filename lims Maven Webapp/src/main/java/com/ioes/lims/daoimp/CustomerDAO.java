package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import com.ioes.lims.beans.Customer;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.ICustomerDAO;

/**
 * A data access object (DAO) providing persistence and search support for Customer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Customer
 * @author MyEclipse Persistence Tools
 */
@Service("customerDAO")
public class CustomerDAO extends AbstractDao implements ICustomerDAO{
	private static final Log log = LogFactory.getLog(CustomerDAO.class);
	// property constants
	public static final String CUSTOMERNAME = "customername";
	public static final String CUSTOMERGENDER = "customergender";
	public static final String CUSTOMERWED = "customerwed";
	public static final String CUSTOMERCERT = "customercert";
	public static final String CUSTOMERCERTNUM = "customercertnum";
	public static final String CUSTOMEREDUC = "customereduc";
	public static final String CUSTOMERMOBILE = "customermobile";
	public static final String CUSTOMERADDR = "customeraddr";
	public static final String CUSTOMERRESID = "customerresid";
	public static final String CUSTOMERCOM = "customercom";
	public static final String CUSTOMERPOST = "customerpost";
	public static final String CUSTOMERCOMADDR = "customercomaddr";
	public static final String CUSTOMERINCOME = "customerincome";
	public static final String CUSTOMERCAR = "customercar";
	public static final String CUSTOMERQQ = "customerqq";
	public static final String CUSTOMERWX = "customerwx";
	public static final String CUSTOMEREMAIL = "customeremail";
	public static final String CUSTOMERHOUSEADDR = "customerhouseaddr";
	public static final String CUSTOMERHPRICE = "customerhprice";
	public static final String CUSTOMERCONTACT1 = "customercontact1";
	public static final String CUSTOMERREALTION1 = "customerrealtion1";
	public static final String CUSTOMERMOBILE1 = "customermobile1";
	public static final String CUSTOMERCONTACT2 = "customercontact2";
	public static final String CUSTOMERREALITION2 = "customerrealition2";
	public static final String CUSTOMERMOBILE2 = "customermobile2";
	public static final String CUSTOMERREPLACE1 = "customerreplace1";
	public static final String CUSTOMERREPLACE2 = "customerreplace2";
	public static final String CUSTOMERDESC = "customerdesc";

	protected void initDao() {
		// do nothing
	}

	@Autowired  
	public void setMySessionFactory(SessionFactory sessionFactory){  
	    super.setSessionFactory(sessionFactory);  
	}  
	public String save(Customer transientInstance) {	
		log.debug("saving Customer instance");
		try {
			List obj_exists=findByProperty("customercertnum",transientInstance.getCustomercertnum());				
			if (obj_exists!=null){
				if (obj_exists.size()!=0) return "300";//300 is account is exists in db
			}
			getHibernateTemplate().save(transientInstance);			
			log.debug("save successful");
			return "200";//success
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("save failed", re);			
			return "250";//db error
		}
	}
	public String merge(Customer detachedInstance) {
		  
		log.debug("merging Customer instance");
		try {
			List obj_exists=findByProperty("customercertnum",detachedInstance.getCustomercertnum());			
			if (obj_exists!=null){				
				
				if ((obj_exists.size()==1 && !((Customer)obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId()))||obj_exists.size()>1) return "300";//300 is double
			}
			Customer result = (Customer) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return "250";//db error
		}
	}
	public void delete(Customer persistentInstance) {
		log.debug("deleting Customer instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Customer findById(java.lang.String id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Customer instance = (Customer) getHibernateTemplate().get("com.ioes.lims.beans.Customer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Customer instance) {
		log.debug("finding Customer instance by example");
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
		log.debug("finding Customer instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Customer as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCustomername(Object customername) {
		return findByProperty(CUSTOMERNAME, customername);
	}

	public List findByCustomergender(Object customergender) {
		return findByProperty(CUSTOMERGENDER, customergender);
	}

	public List findByCustomerwed(Object customerwed) {
		return findByProperty(CUSTOMERWED, customerwed);
	}

	public List findByCustomercert(Object customercert) {
		return findByProperty(CUSTOMERCERT, customercert);
	}

	public List findByCustomercertnum(Object customercertnum) {
		return findByProperty(CUSTOMERCERTNUM, customercertnum);
	}

	public List findByCustomereduc(Object customereduc) {
		return findByProperty(CUSTOMEREDUC, customereduc);
	}

	public List findByCustomermobile(Object customermobile) {
		return findByProperty(CUSTOMERMOBILE, customermobile);
	}

	public List findByCustomeraddr(Object customeraddr) {
		return findByProperty(CUSTOMERADDR, customeraddr);
	}

	public List findByCustomerresid(Object customerresid) {
		return findByProperty(CUSTOMERRESID, customerresid);
	}

	public List findByCustomercom(Object customercom) {
		return findByProperty(CUSTOMERCOM, customercom);
	}

	public List findByCustomerpost(Object customerpost) {
		return findByProperty(CUSTOMERPOST, customerpost);
	}

	public List findByCustomercomaddr(Object customercomaddr) {
		return findByProperty(CUSTOMERCOMADDR, customercomaddr);
	}

	public List findByCustomerincome(Object customerincome) {
		return findByProperty(CUSTOMERINCOME, customerincome);
	}

	public List findByCustomercar(Object customercar) {
		return findByProperty(CUSTOMERCAR, customercar);
	}

	public List findByCustomerqq(Object customerqq) {
		return findByProperty(CUSTOMERQQ, customerqq);
	}

	public List findByCustomerwx(Object customerwx) {
		return findByProperty(CUSTOMERWX, customerwx);
	}

	public List findByCustomeremail(Object customeremail) {
		return findByProperty(CUSTOMEREMAIL, customeremail);
	}

	public List findByCustomerhouseaddr(Object customerhouseaddr) {
		return findByProperty(CUSTOMERHOUSEADDR, customerhouseaddr);
	}

	public List findByCustomerhprice(Object customerhprice) {
		return findByProperty(CUSTOMERHPRICE, customerhprice);
	}

	public List findByCustomercontact1(Object customercontact1) {
		return findByProperty(CUSTOMERCONTACT1, customercontact1);
	}

	public List findByCustomerrealtion1(Object customerrealtion1) {
		return findByProperty(CUSTOMERREALTION1, customerrealtion1);
	}

	public List findByCustomermobile1(Object customermobile1) {
		return findByProperty(CUSTOMERMOBILE1, customermobile1);
	}

	public List findByCustomercontact2(Object customercontact2) {
		return findByProperty(CUSTOMERCONTACT2, customercontact2);
	}

	public List findByCustomerrealition2(Object customerrealition2) {
		return findByProperty(CUSTOMERREALITION2, customerrealition2);
	}

	public List findByCustomermobile2(Object customermobile2) {
		return findByProperty(CUSTOMERMOBILE2, customermobile2);
	}

	public List findByCustomerreplace1(Object customerreplace1) {
		return findByProperty(CUSTOMERREPLACE1, customerreplace1);
	}

	public List findByCustomerreplace2(Object customerreplace2) {
		return findByProperty(CUSTOMERREPLACE2, customerreplace2);
	}

	public List findByCustomerdesc(Object customerdesc) {
		return findByProperty(CUSTOMERDESC, customerdesc);
	}

	public List findAll() {
		log.debug("finding all Customer instances");
		try {
			String queryString = "from Customer";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	
	public void attachDirty(Customer instance) {
		log.debug("attaching dirty Customer instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Customer instance) {
		log.debug("attaching clean Customer instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CustomerDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CustomerDAO) ctx.getBean("CustomerDAO");
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
			ps = conn.prepareStatement("delete from customer where id=?");
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
	public List findByCname(Object cname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCgender(Object cgender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCwed(Object cwed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcert(Object ccert) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcertnum(Object ccertnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCeduc(Object ceduc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCmobile(Object cmobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCaddr(Object caddr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCresid(Object cresid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcom(Object ccom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCpost(Object cpost) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcomaddr(Object ccomaddr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCincome(Object cincome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcar(Object ccar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCqq(Object cqq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCwx(Object cwx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCemail(Object cemail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByChouseaddr(Object chouseaddr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByChprice(Object chprice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcontact1(Object ccontact1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCrealtion1(Object crealtion1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCmobile1(Object cmobile1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCcontact2(Object ccontact2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCrealition2(Object crealition2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCmobile2(Object cmobile2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCreplace1(Object creplace1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCreplace2(Object creplace2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCdesc(Object cdesc) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	/**
	 *模糊查找，根据姓名的拼音字母查询名字
	 */
	public List<LinkedHashMap<String, Object>> getCustomerInfo(String firstNameChar) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select * from customer where firstPinyin(`customerreplace1`) =? or pinyin(`customerreplace1`) like ? or firstPinyin(`customerreplace2`) =? or pinyin(`customerreplace2`) like ? or firstPinyin(`customername`) =? or pinyin(`customername`) like ? or customercertnum like ?";
		
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
					ps.setString(3, firstNameChar);
					ps.setString(4, "%"+firstNameChar+"%");
					ps.setString(5, firstNameChar);
					ps.setString(6, "%"+firstNameChar+"%");
					ps.setString(7, "%"+firstNameChar+"%");
				}else{					
					
					sqlstring = "select * from customer limit 0,20";
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
}