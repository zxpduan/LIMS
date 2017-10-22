package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Pact;
import com.ioes.lims.beans.Permoney;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.beans.User;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IPactDAO;
import com.ioes.lims.idao.IRatioDAO;
import com.ioes.lims.idao.IRepayplanDAO;
import com.ioes.lims.utils.CalcuInterest;
import com.ioes.lims.utils.StringUtil;
import com.sias.util.SessionUtil;
import com.sias.util.dataTypeUtil;
import com.sias.util.dateUtils;

/**
 * A data access object (DAO) providing persistence and search support for Pact entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Pact
 * @author MyEclipse Persistence Tools
 */
@Service("pactDAO")
public class PactDAO extends AbstractDao implements IPactDAO {
	private static final Log log = LogFactory.getLog(PactDAO.class);
	// property constants
	public static final String CTTCUSTOMER = "cttcustomer";
	public static final String CTTPROJECT = "cttproject";
	public static final String CTTDISCOUNT = "cttdiscount";
	public static final String CTTSERIAL = "cttserial";
	public static final String CTTCODE = "cttcode";
	public static final String CTTBANK = "cttbank";
	public static final String CTTBANKCARD = "cttbankcard";
	public static final String CTTAMOUNT = "cttamount";
	public static final String CTTAMOUNTU = "cttamountu";
	public static final String CTTCATE = "cttcate";
	public static final String CTTRATIO = "cttratio";
	public static final String CTTREPAYCATE = "cttrepaycate";
	public static final String CTTFUN = "cttfun";
	public static final String CTTPRICE = "cttprice";
	public static final String CTTSELFPRICE = "cttselfprice";
	public static final String CTTREPAYDAY = "cttrepayday";
	public static final String CTTCARLOC = "cttcarloc";
	public static final String CTTHOUSELOC = "ctthouseloc";
	public static final String OPERATOR = "operator";
	public static final String AUDITSTATE = "auditstate";
	
	@Autowired
	IRepayplanDAO repayplanDao;
	@Autowired
	IRatioDAO ratioDao;
	
	public List findByAuditstate(Object auditstate) {
		return findByProperty(AUDITSTATE, auditstate);
	}
	protected void initDao() {
		// do nothing
	}

	public void delete(Pact persistentInstance) {
		log.debug("deleting Pact instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Pact findById(java.lang.String id) {
		log.debug("getting Pact instance with id: " + id);
		try {
			Pact instance = (Pact) getHibernateTemplate().get("com.ioes.lims.beans.Pact", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Pact instance) {
		log.debug("finding Pact instance by example");
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
		log.debug("finding Pact instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Pact as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCttcustomer(Object cttcustomer) {
		return findByProperty(CTTCUSTOMER, cttcustomer);
	}

	public List findByCttproject(Object cttproject) {
		return findByProperty(CTTPROJECT, cttproject);
	}

	public List findByCttdiscount(Object cttdiscount) {
		return findByProperty(CTTDISCOUNT, cttdiscount);
	}

	public List findByCttserial(Object cttserial) {
		return findByProperty(CTTSERIAL, cttserial);
	}

	public List findByCttcode(Object cttcode) {
		return findByProperty(CTTCODE, cttcode);
	}

	public List findByCttbank(Object cttbank) {
		return findByProperty(CTTBANK, cttbank);
	}

	public List findByCttbankcard(Object cttbankcard) {
		return findByProperty(CTTBANKCARD, cttbankcard);
	}

	public List findByCttamount(Object cttamount) {
		return findByProperty(CTTAMOUNT, cttamount);
	}

	public List findByCttamountu(Object cttamountu) {
		return findByProperty(CTTAMOUNTU, cttamountu);
	}

	public List findByCttcate(Object cttcate) {
		return findByProperty(CTTCATE, cttcate);
	}

	public List findByCttratio(Object cttratio) {
		return findByProperty(CTTRATIO, cttratio);
	}

	public List findByCttrepaycate(Object cttrepaycate) {
		return findByProperty(CTTREPAYCATE, cttrepaycate);
	}

	public List findByCttfun(Object cttfun) {
		return findByProperty(CTTFUN, cttfun);
	}

	public List findByCttprice(Object cttprice) {
		return findByProperty(CTTPRICE, cttprice);
	}

	public List findByCttselfprice(Object cttselfprice) {
		return findByProperty(CTTSELFPRICE, cttselfprice);
	}

	public List findByCttrepayday(Object cttrepayday) {
		return findByProperty(CTTREPAYDAY, cttrepayday);
	}

	public List findByCttcarloc(Object cttcarloc) {
		return findByProperty(CTTCARLOC, cttcarloc);
	}

	public List findByCtthouseloc(Object ctthouseloc) {
		return findByProperty(CTTHOUSELOC, ctthouseloc);
	}

	public List findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	public List findAll() {
		log.debug("finding all Pact instances");
		try {
			String queryString = "from Pact";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public void attachDirty(Pact instance) {
		log.debug("attaching dirty Pact instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Pact instance) {
		log.debug("attaching clean Pact instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PactDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PactDAO) ctx.getBean("PactDAO");
	}
	/**
	 * 为安全起见，不删除还款记录，逾期和还款计划
	 */
	@Override
	public void delBatch(String[] ids) {
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
			ps = conn.prepareStatement("delete from pact where id=?");
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
	public String save(Pact transientInstance,String indicant) {
		log.debug("saving Pact instance");
		try {
			int serial=0;
			if (!indicant.equalsIgnoreCase("0")){//auto serial			
				serial=getSerial(transientInstance);//先获取最新流水，并更新最新流水+1,并发条件下可能会重复
				transientInstance.setCttserial(serial);
			}else{
				serial=transientInstance.getCttserial();
			}
			//judge if the serial is exists
			System.out.println(transientInstance.getCttserial());
			if (isSerialExists(transientInstance)){
				return "301";// 300 is serial in project is exists in db
			}
			String code=transientInstance.getCttcode().substring(0,transientInstance.getCttcode().length()-3)+StringUtil.StrtoFullstr(serial+"", 3);
			transientInstance.setCttcode(code);
			List obj_exists = findByProperty("cttcode", transientInstance.getCttcode());
			if (obj_exists != null) {
				if (obj_exists.size() != 0)
					return "300";// 300 is account is exists in db
			}			
			getHibernateTemplate().save(transientInstance);
			if (indicant.equalsIgnoreCase("0")){//改变该项目下的流水号记录最大值
				serialAdd(transientInstance);
			}
			log.debug("save successful");
			return "200";// success
		} catch (RuntimeException re) {
			log.error("save failed", re);
			return "250";// db error
		}
	}
	/**
	 * get serial of pack under same project
	 *
	 * @return 2017年6月24日 by 周喜平
	 */
	public int getSerial(Pact transientInstance){
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select serial from project where id=? for update";
		String sqlstring2 = "update project set serial=serial+1 where  id=?";
		int re=0;
		ResultSet rSet = null;
		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			conn.setAutoCommit(false);					
			ps = conn.prepareStatement(sqlstring);
			ps2 = conn.prepareStatement(sqlstring2);
			ps.setString(1, transientInstance.getCttproject());
			ps2.setString(1, transientInstance.getCttproject());
			rSet = ps.executeQuery();
				
			if (rSet.next()) {
				re=rSet.getInt(1);
				ps2.executeUpdate();
				
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error("error 4:", e);
		} finally {			
			try {
				conn.setAutoCommit(true);
				if (null != rSet) {
					rSet.close();					
				}
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
			return re;
		}
		
	}
	/**
	 * make the serial of project + 1
	 *
	 * @param transientInstance
	 * @return 2017年10月6日 by 周喜平
	 */
	public int serialAdd(Pact transientInstance){
		Connection conn = null;
		PreparedStatement ps = null;
		Session sess=null;
		
		String sqlstring = "update project set serial=? where  id=? and serial<=?";
		int re=0;
		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			conn.setAutoCommit(false);					
			ps = conn.prepareStatement(sqlstring);
			ps.setInt(1, transientInstance.getCttserial()+1);
			ps.setString(2, transientInstance.getCttproject());
			ps.setInt(3, transientInstance.getCttserial()+1);
			re=ps.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error("error 4:", e);
		} finally {			
			try {
				conn.setAutoCommit(true);
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
			return re;
		}
		
	}
	/**
	 * judge if  serial is exists
	 *
	 * @param transientInstance
	 * @return 2017年10月6日 by 周喜平
	 */
	public boolean isSerialExists(Pact transientInstance){
		Connection conn = null;
		PreparedStatement ps = null;		
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select * from pact where cttproject=? and cttserial=?";		
		boolean re=false;
		ResultSet rSet = null;		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
								
			ps = conn.prepareStatement(sqlstring);
			
			ps.setString(1, transientInstance.getCttproject());
			ps.setInt(2, transientInstance.getCttserial());
			rSet = ps.executeQuery();				
			if (rSet.next()) {
				re=true;	
			}
		} catch (Exception e) {			
			e.printStackTrace();
			re=true;
			logger.error("error 4:", e);
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
			return re;
		}
		
	}
	@Override
	public String merge(Pact detachedInstance) {
		
		log.debug("merging Pact instance");
		try {
			List obj_exists = findByProperty("cttcode", detachedInstance.getCttcode());
			if (obj_exists != null) {

				if ((obj_exists.size() == 1 && !((Pact) obj_exists.get(0)).getId().equalsIgnoreCase(detachedInstance.getId())) || obj_exists.size() > 1)
					return "300";// 300 is double
			}
			Pact result = (Pact) getHibernateTemplate().merge(detachedInstance);
			serialAdd(detachedInstance);
			log.debug("merge successful");
			return "200";//success
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			re.printStackTrace();
			return "250";//db error
		}		
	}
	/**
	 * pass audit,update audit state and add repayment plan recrod
	 */
	@Override
	public String updateAuditPact(Pact detachedInstance) {
		
		log.debug("merging Pact instance");
		try {
			
			double cost=detachedInstance.getCttamount();//loan amount
			int instalment=detachedInstance.getCttcate();//loan instalment
			double realcostSum=0;
			double ratio=ratioDao.findById(detachedInstance.getCttratio()).getRatio();//loan ratio
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			CalcuInterest cc=new CalcuInterest(cost, ratio, instalment,dateFormat.format(detachedInstance.getCttrepayday()));//calculate repay plan
			List<Permoney> ll;//计算出还款计划
			if (detachedInstance.getCalculatecate()==1){//分段法
				ll=cc.methodOne();
			}else{//普通法
				ll=cc.methodTow();
			}			
			//create repay day list
			List del_os=repayplanDao.findByPid(detachedInstance.getId());
			for (Object object : del_os) {
				repayplanDao.delete((Repayplan)object);//delete old record
			}			
			//repayplanDao.deleteBypid(detachedInstance.getId());		
			//recording repay plan
			int n=1;
			for (Permoney permoney : ll) {// recording repay plan records
				Repayplan rp=new Repayplan();				
				rp.setId(SessionUtil.getSessionString());
				rp.setAddtime(new Timestamp(System.currentTimeMillis()));
				rp.setCost(permoney.getCost());//cost per times
				rp.setInterest(permoney.getInterest());//interest per times
				rp.setPaytimes(permoney.getInstalmentno());//the number to repay,还款期数的序号
				rp.setPid(detachedInstance.getId());
				rp.setPayamount(permoney.getAmount());
				rp.setPayday(dateFormat.parse(permoney.getPayDay()));
				
				if (n<=detachedInstance.getCtthpaytimes()){
					realcostSum+=permoney.getCost();
				}
				n++;
				repayplanDao.save(rp);
			}			
			
			//modify initial data,such as \cttpaymonth\\\\\cttnowcost\\cttnowinterest\\\
			//set again when save repay records
			if (detachedInstance.getCtthpaytimes()!=0){//已有还款记录了
				detachedInstance.setCttpaymonth(ll.get(detachedInstance.getCtthpaytimes()-1).getAmount());
				detachedInstance.setCttnowcost(ll.get(detachedInstance.getCtthpaytimes()-1).getCost());
				detachedInstance.setCttnowinterest(ll.get(detachedInstance.getCtthpaytimes()-1).getInterest());
				detachedInstance.setNextrepayday(dateFormat.parse(ll.get(detachedInstance.getCtthpaytimes()-1).getPayDay()));
				detachedInstance.setCttspaytimes(detachedInstance.getCtthpaytimes()+1);
				//处理贷款余额				
				 if (detachedInstance.getCttcate().equals(detachedInstance.getCtthpaytimes())){//repay over
					 detachedInstance.setCttbalance(0d);
					 detachedInstance.setAuditstate(4);
				 }else{//repay some 
					 
					 detachedInstance.setCttbalance(detachedInstance.getCttamount()-realcostSum);
				 }
			}else{//没有还款
				detachedInstance.setCttpaymonth(ll.get(0).getAmount());
				detachedInstance.setCtthpaytimes(0);
				detachedInstance.setCttspaytimes(1);
				detachedInstance.setCttnowcost(ll.get(0).getCost());
				detachedInstance.setCttnowinterest(ll.get(0).getInterest());
				detachedInstance.setNextrepayday(dateFormat.parse(ll.get(0).getPayDay()));				
			}
			detachedInstance.setCttloadenddate(dateFormat.parse(ll.get(ll.size()-1).getPayDay()));
			Pact result = (Pact) getHibernateTemplate().merge(detachedInstance);//modify audti state
			log.debug("merge successful");
			return "200";//success
		} catch (Exception re) {
			log.error("merge failed", re);
			re.printStackTrace();
			return "250";//db error
		}		
	}

	@Override
	public List<LinkedHashMap<String, Object>> getPactInfo(String firstNameChar) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select t1.cttcode,t1.id,t2.customername from pact t1 join customer t2 on t1.cttcustomer=t2.id where firstPinyin(`customername`) =? or pinyin(`customername`) like ?  or cttcode like ? union  select  t1.cttcode,t1.id,t2.customerreplace1 from pact t1 join customer t2 on t1.cttcustomer=t2.id where  firstPinyin(`customerreplace1`) =? or pinyin(`customerreplace1`) like ?  UNION select  t1.cttcode,t1.id,t2.customerreplace2 from pact t1 join customer t2 on t1.cttcustomer=t2.id where  firstPinyin(`customerreplace2`) =? or pinyin(`customerreplace2`) like ? ";
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
					ps.setString(4, firstNameChar);
					ps.setString(5, "%"+firstNameChar+"%");
					ps.setString(6, firstNameChar);
					ps.setString(7, "%"+firstNameChar+"%");
				}else{					
					
					sqlstring = "select  t1.cttcode,t1.id,t2.customername,t1.ctttaa,t1.nextrepayday,t1.ctthpaytimes,t1.auditstate,t1.cttpaymonth from pact t1 join customer t2 on t1.cttcustomer=t2.id  limit 0,20";
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
	public int findMaxSerial(Pact instance) {
		try {
			String queryString = "select MAX(p.cttserial) from Pact p where p.cttproject='"+instance.getCttproject()+"'";
			List re= getHibernateTemplate().find(queryString);
			if (re==null){
				return 0;
			}else{
				if (re.size()==0) return 0;
				else {
					return 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	@Override
	/**
	 * get pact infomation ,include customer name or customerreplace1,2 name, and instalment and arrears
	 */
	public List<LinkedHashMap<String, Object>> getPactInfo2(String firstNameChar) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select t1.cttcode,t1.id,t2.customername,t1.ctttaa,t1.nextrepayday,t1.ctthpaytimes,t1.auditstate,t1.cttpaymonth from (select * from pact where auditstate=2 or auditstate=4) t1 join customer t2 on t1.cttcustomer=t2.id where customername like ? or firstPinyin(`customername`) =? or pinyin(`customername`) like ?  or cttcode like ? "
				+ "union  select  t1.cttcode,t1.id,t2.customerreplace1,t1.ctttaa,t1.nextrepayday,t1.ctthpaytimes,t1.auditstate,t1.cttpaymonth from (select * from pact where auditstate=2 or auditstate=4) t1 join customer t2 on t1.cttcustomer=t2.id where  customername  like ? or   firstPinyin(`customerreplace1`) =? or pinyin(`customerreplace1`) like ?  "
				+ "UNION select  t1.cttcode,t1.id,t2.customerreplace2,t1.ctttaa,t1.nextrepayday,t1.ctthpaytimes,t1.auditstate,t1.cttpaymonth from (select * from pact where auditstate=2 or auditstate=4) t1 join customer t2 on t1.cttcustomer=t2.id where  customername  like ? or   firstPinyin(`customerreplace2`) =? or pinyin(`customerreplace2`) like ? ";
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
					ps.setString(1, "%"+firstNameChar+"%");
					ps.setString(2, firstNameChar);
					ps.setString(3, "%"+firstNameChar+"%");					
					ps.setString(4, "%"+firstNameChar+"%");
					
					ps.setString(5, "%"+firstNameChar+"%");
					ps.setString(6, firstNameChar);
					ps.setString(7, "%"+firstNameChar+"%");
					
					ps.setString(8, "%"+firstNameChar+"%");
					ps.setString(9, firstNameChar);
					ps.setString(10, "%"+firstNameChar+"%");
				}else{					
					
					sqlstring = "select  t1.cttcode,t1.id,t2.customername,t1.ctttaa,t1.nextrepayday,t1.ctthpaytimes,t1.auditstate,t1.cttpaymonth from pact t1 join customer t2 on t1.cttcustomer=t2.id  limit 0,20";
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
	/**
	 * 通过id查询提前还款时提前应还的金额=贷款余额+本次月供
	 */
	@Override
	public double findLastMoneyById(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String today=dateFormat.format(new Date());
		String sqlstring = 	"select (leftcost+interest+ctttaa) as leftmoney"
		+ "	from ("
		+ "select id, cttbalance as leftcost from pact where id=?) m1"
		+ "	join ("
		+ "select pid, if(t1.nextrepayday>?,interest,0) as interest,t1.ctttaa from repayplan t join (select * from pact where id=?) t1 on t.pid=t1.id where t.paytimes=t1.ctthpaytimes+1"
		+ ") m2 on m1.id=m2.pid";
		double re=0;		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
			ps = conn.prepareStatement(sqlstring);
			
			ps.setString(1, id);
			ps.setString(2, today);
			ps.setString(3, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				re=rs.getDouble(1);
			}
				
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error("error 4:", e);
			
		} finally {
			try {
				if (null != rs) {
					rs.close();					
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
		return re;	
	}
}