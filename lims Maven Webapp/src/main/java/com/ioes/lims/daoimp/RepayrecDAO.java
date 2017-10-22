package com.ioes.lims.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.enterprise.inject.New;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.ioes.lims.action.repayAction;
import com.ioes.lims.beans.Overdue;
import com.ioes.lims.beans.Pact;
import com.ioes.lims.beans.Payarrearsrec;
import com.ioes.lims.beans.Repay;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.beans.Repayrec;
import com.ioes.lims.beans.Sysparam;
import com.ioes.lims.dao.AbstractDao;
import com.ioes.lims.idao.IOverdueDAO;
import com.ioes.lims.idao.IPactDAO;
import com.ioes.lims.idao.IPayarrearsrecDAO;
import com.ioes.lims.idao.IRatioDAO;
import com.ioes.lims.idao.IRepayplanDAO;
import com.ioes.lims.idao.IRepayrecDAO;
import com.ioes.lims.idao.ISysparamDAO;
import com.ioes.lims.utils.CalcuInterest;
import com.ioes.lims.utils.StringUtil;
import com.sias.util.SessionUtil;
import com.sias.util.dateUtils;

/**
 * A data access object (DAO) providing persistence and search support for Repayrec entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the desired type of transaction control.
 * 
 * @see com.ioes.lims.beans.Repayrec
 * @author MyEclipse Persistence Tools
 */
@Service("repayrecDAO")
public class RepayrecDAO extends AbstractDao implements IRepayrecDAO {
	private static final Log log = LogFactory.getLog(RepayrecDAO.class);
	// property constants
	public static final String PID = "pid";
	public static final String SAMOUNT = "samount";
	public static final String AMOUNT = "amount";
	public static final String REPID = "repid";
	public static final String INSTALMENT = "instalment";
	public static final String COST = "cost";
	public static final String INTEREST = "interest";
	public static final String OVERINTEREST = "overinterest";
	public static final String ARREARS = "arrears";
	public static final String AMOUNTMON = "amountmon";

	@Autowired
	public IPactDAO pactDao;
	@Autowired
	public IRepayplanDAO repayplanDao;
	@Autowired
	public IOverdueDAO overdueDAO;
	@Autowired
	public IRatioDAO ratioDAO;
	@Autowired
	IPayarrearsrecDAO payarrearsrecDAO;
	@Autowired
	public ISysparamDAO sysparamDAO;
	protected void initDao() {
		// do nothing
	}

	public void save(Repayrec transientInstance) {
		log.debug("saving Repayrec instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Repayrec persistentInstance) {
		log.debug("deleting Repayrec instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Repayrec findById(java.lang.String id) {
		log.debug("getting Repayrec instance with id: " + id);
		try {
			Repayrec instance = (Repayrec) getHibernateTemplate().get("com.ioes.lims.beans.Repayrec", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Repayrec instance) {
		log.debug("finding Repayrec instance by example");
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
		log.debug("finding Repayrec instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Repayrec as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	
	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByRepid(Object repid) {
		return findByProperty(REPID, repid);
	}

	public List findByInstalment(Object instalment) {
		return findByProperty(INSTALMENT, instalment);
	}

	public List findAll() {
		log.debug("finding all Repayrec instances");
		try {
			String queryString = "from Repayrec";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Repayrec merge(Repayrec detachedInstance) {
		log.debug("merging Repayrec instance");
		try {
			Repayrec result = (Repayrec) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Repayrec instance) {
		log.debug("attaching dirty Repayrec instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Repayrec instance) {
		log.debug("attaching clean Repayrec instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RepayrecDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RepayrecDAO) ctx.getBean("RepayrecDAO");
	}

	@Override
	public void delBatch(String[] ids) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 查询某一合同某期还款总额
	 *
	 * @param pid
	 * @param instalment 2017年7月17日 by 周喜平
	 */
	public double getSomeRepayTotal(String pid,int instalment) {
		Session sess=null;
		Connection conn = null;
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			ps = conn.prepareStatement("select sum(t.cost+t.interest+payarrears) from repayrec t where instalment=? and pid=?");
			ps.setInt(1, instalment);
			ps.setString(2, pid);
			rs=ps.executeQuery();
			if (rs.next()){
				return rs.getDouble(1);
			}else{
				return 0d;
			}
			
		} catch (Exception e) {						
			e.printStackTrace();
			return 0d;
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
	/**
	 * 
	 * 保存还款记录
	 * 更新pact状态和数据
	 * 保存或修改overdue记录
	 * 保存还欠款记录
	 * etc.
	 */
	@Override
	public String saveAll(List<Repay> repay) {
		log.debug("repay recording");
		String reState="200";//success
		try {
			if (repay.size()==0) return reState;//no data submit			
			
			for (Repay transientInstance : repay) {
				saveSingle(transientInstance);
			}			
			log.debug("saveAll successful");
			
		} catch (RuntimeException re) {
			log.error("repay recording failed", re);
			reState="250";//fail
			re.printStackTrace();
			throw re;
		}		
		return reState;
	}
	/**
	 * 还欠款后修改逾期
	 * 2017年7月7日 by 周喜平
	 */
	public void modifyOverdue(){
		
	}
	
	/**
	 * 得到历史欠款记录，按照先后发生的顺序排列，最早的在前
	 * model.arrears>0说明有欠款，
	 * model.arrears>0 and model.arrears<>model.payarrears说明欠款没有还，或者欠款没还完，每还一笔欠款，就会更新model.payarrears
	 * 2017年7月7日 by 周喜平
	 */	
	public Repayrec getArrearsRec(String pid){
		log.debug("finding arrears Repayrec ");
		try {
			String queryString = "from Repayrec as model where pid=? order by model.instalment desc,addtime desc";
			
			List<Repayrec> llList= getHibernateTemplate().find(queryString,pid);
			if (llList!=null && llList.size()>0) return llList.get(0);
			else return null;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	/**
	 * 获得本期历史已还本金和利息
	 *
	 * @param pid
	 * @param instalment
	 * @return 2017年8月8日 by 周喜平
	 */
	public double[] getHistoryCostInterest(String pid,int instalment) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select sum(cost) as cost,sum(interest) as insterest from repayrec where pid=? and instalment=? group by instalment";
		double[] re=new double[2];
		ResultSet rSet = null;
		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();
			
			ps = conn.prepareStatement(sqlstring);
			ps.setString(1, pid);
			ps.setInt(2, instalment);
			
			rSet = ps.executeQuery();
			
			while (rSet.next()) {
				re[0]=rSet.getDouble(1);//cost
				re[1]=rSet.getDouble(2);//interest
			}
			return re;
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error("error in findBeOver:", e);
			return re;
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
	}
	/**
	 * 找到所有逾期的记录
	 */
	@Override
	public List<LinkedHashMap<String, Object>> findBeOver() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Session sess=null;
		String sqlstring = "select * from ("
				+ "select * from ("
				+ "select * from (select pid,id as planid,paytimes,payday,payamount from repayplan  where payday>=? and payday<=? and payday<= ?) t_0"
				+ " join (select * from pact where auditstate=2) t_1 on t_0.pid=t_1.id) t"
				+ " where not exists (	select * from (select pid,repid,instalment, sum(cost+interest+payarrears) as total from repayrec  GROUP BY pid,repid,instalment ) t1 where t.pid=t1.pid and t.planid=t1.repid and t1.total >=t.payamount)"
				+ ") t2 join ( select t_2.pid,(t_2.payamount-IFNULL(t_3.cost,0)+IFNULL(t_3.interest,0)+IFNULL(t_3.payarrears,0)) as per_arrears from (select * from repayplan where payday>=? and payday<=? and payday<= ?) t_2 left outer join repayrec t_3 on t_2.id=t_3.repid ) t_4"
				+ "	on t2.id=t_4.pid"
				+ " LEFT OUTER JOIN (select * from overdue where startdate>=? and startdate<=?) t3 on t2.pid=t3.pid";
		
		List<LinkedHashMap<String, Object>> searchRs = new ArrayList<LinkedHashMap<String, Object>>();
		ResultSetMetaData rsmd = null;
		String key = null;
		Object value = null;
		LinkedHashMap map = null;
		ResultSet rSet = null;
		
		try {
			sess=this.getHibernateTemplate().getSessionFactory().openSession();			
			conn = sess.connection();		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String	start=dateUtils.getStringFromDate(new Date())+"-01";
			String today=dateFormat.format(new Date());
			String	end=dateUtils.getEndDateString(dateFormat.format(new Date()));	
			
			ps = conn.prepareStatement(sqlstring);
			
			ps.setString(1, start);
			ps.setString(2, end);
			ps.setString(3, today);
			ps.setString(4, start);
			ps.setString(5, end);
			ps.setString(6, today);
			ps.setString(7, start);
			ps.setString(8, end);
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
			logger.error("error in findBeOver:", e);
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
	 * 递归还款
	 * 还欠款，还本期本金，还本期利息，还有结余就递归还款
	 * 2.还欠款，记录还了哪笔欠款payarrearsrec，并更新欠款记录中还款额度overdue
	 * 3.还本金，换利息
	 * 4.记录本次还款repayrec
	 * 5.有结余，更新pact中历史欠款，还款次数，下一次还款日，递归。
	 * 6.无结余，更新pact中历史欠款，还款次数，下一次还款日，递归，结束还款
	 */
	@Override
	public void saveSingle(Repay repay) {
		try {
			Pact pact=pactDao.findById(repay.getPid());//找到要还的合同
			double arrears=pact.getCtttaa();//获取历史欠款,有正负之分，正欠款，负结余			
			double payamount=pact.getCttpaymonth();//月供
			//System.out.println("repay.getPayamount()"+repay.getPayamount()+":(arrears+payamount)"+(arrears)+":"+(payamount));
			if (repay.getPayamount()>(arrears+payamount)){//有结余
				//System.out.println("saveSingle--"+"有结余");
				repayMore(repay, pact);
			}else if(repay.getPayamount()==(arrears+payamount)){//刚好还完，主要是涉及到是否修改新的还款日期和还款期数
				//System.out.println("saveSingle--"+"刚好还完");
				if (arrears<=0){//无历史欠款
					//System.out.println("saveSingle--"+"刚好还完，无欠款");
					repayOver(repay, pact);
				}else{//有历史欠款
					//System.out.println("saveSingle--"+"刚好还完，有欠款");
					repayMore(repay, pact);					
				}				
			}else{//还是没有还完，产生新的欠款情况，不更新还款期数和下次还款日期
				//System.out.println("saveSingle--"+"还是没有还完，产生新的欠款情况");
				repayLess(repay, pact);
			}			
		} catch (RuntimeException re) {
			logger.error("error in repayrecDao:",re);
			throw re;
		}		
	}
	/**
	 * 还的多 ，实际还款>历史欠款+本次月供
	 * @param repay
	 * @return 2017年7月14日 by 周喜平
	 */
	public void repayMore(Repay repay,Pact pact){
		try {						
			Date nextDate=repayOver(repay, pact);//先执行还完部分，下面执行结余部分
			//System.out.println("repayMore--"+"先执行还完部分");
			if (nextDate!=null){//is null 说明还完了，就结余，不再还款
				//System.out.println(balance);
				repay.setPayamount(0d);
				repay.setRepayday(nextDate);
				saveSingle(repay);//执行结余部分
				//System.out.println("repayMore--"+"执行结余部分");
			}			
		} catch (RuntimeException re) {
			logger.error("error in repayrecDao:",re);
			throw re;
		}	
		
		
	}
	/**
	 * 刚好够还，包括能够还完历史欠款+本次月供，允许还的多，可以理解为只还了本次的记录，要还更多的记录，配合repayMore
	 *
	 * @param repay 2017年7月14日 by 周喜平
	 */
	public Date repayOver(Repay repay,Pact pact){
		//逾期天数
		int overdueDay=(int)dateUtils.getDiffDays(dateFormat.format(repay.getOperday()),dateFormat.format(repay.getRepayday()));
		//还历史欠款
		Date redate=null;
		Repayrec arrearsRec=null;//历史欠款记录
		Repayrec rpc=new Repayrec();
		int times=pact.getCttspaytimes();
		double overinterest=0d;
		double arrears=0d;
		Repayplan rep0=repayplanDao.findByPidPaytime(pact.getId(),times);//现在的还款计划
		
		boolean isarrears=false;
		if (pact.getCtttaa()>0){//有历史欠款
			//System.out.println("repayOver--"+"有历史欠款");
			isarrears=true;
			arrearsRec=getArrearsRec(pact.getId());				
				if (arrearsRec!=null) {//开始还历史，最后amount才交给本次还款					  
					    arrearsRec.setPayarrears(arrearsRec.getArrears());
						Payarrearsrec prs=new Payarrearsrec();
						prs.setId(SessionUtil.getSessionString());
						prs.setAddtime(new Timestamp(System.currentTimeMillis()));
						prs.setPid(pact.getId());
						prs.setPlanid(arrearsRec.getRepid());
						prs.setRepayamount(arrearsRec.getArrears());
						Overdue ord=overdueDAO.findbypidplanid(pact.getId(),arrearsRec.getRepid());//找到对应欠款逾期记录，修改还款						
						if (ord!=null){						
							ord.setPayoff(ord.getAmount());
							overinterest=ord.getOverinterest();
							overdueDAO.merge(ord);
						}	
						payarrearsrecDAO.save(prs);//保存还欠款记录											
						merge(arrearsRec);//更新历史还款记录，加入还额度
						
					}
				//times++;//有历史欠款，还完后，要产生新的还款记录
		}else{//没有历史欠款，也有可能存在逾期
			//System.out.println("repayOver--"+"没有历史欠款,可能存在逾期");
			//处理逾期		
			//先判断是否有逾期
			Overdue ord=overdueDAO.findbypidplanid(pact.getId(), rep0.getId());//找到对应欠款逾期记录，修改还款			
			if (ord!=null){				
				ord.setPayoff(ord.getAmount());	
				overinterest=ord.getOverinterest();
				overdueDAO.merge(ord);
			}	
		}
		
		
		//还本次,如果历史欠款还完了，说明历史上最后一笔也还清了，那么现在再还的话，一定是新的
		
		if (times <pact.getCttcate()){//还没有还完期数
			if (isarrears){//有历史欠款
				//System.out.println("repayOver--"+"还完历史欠款，还本次");				
				arrears=pact.getCtttaa()-repay.getPayamount();//当期的欠款，仅是当期的
				pact.setCtthpaytimes(times);
				pact.setCttspaytimes(times+1);			
				//新的还款日期
				redate=repayplanDao.findByPidPaytime(pact.getId(),times+1).getPayday();
				pact.setNextrepayday(redate);
				pact.setCtttaa(pact.getCtttaa()-repay.getPayamount());
				//
				
			}else{//无历史欠款，可能有结余pact.getCtttaa()<0
				//System.out.println("repayOver--"+"无历史欠款");				
				arrears=(pact.getCttpaymonth()+pact.getCtttaa())-repay.getPayamount();//当期的欠款，仅是当期的
				pact.setCtthpaytimes(times);
				pact.setCttspaytimes(times+1);			
				//新的还款日期
				redate=repayplanDao.findByPidPaytime(pact.getId(),times+1).getPayday();
				pact.setNextrepayday(redate);
				pact.setCtttaa((pact.getCttpaymonth()+pact.getCtttaa())-repay.getPayamount());
				
			}
			//贷款余额是本金余额，每次还款时，减去还的本金，不能减利息
			if (pact.getCttbalance()-rep0.getCost()<=0){
				pact.setCttbalance(0d);
			}else{
				pact.setCttbalance(pact.getCttbalance()-rep0.getCost());
			}
			pactDao.merge(pact);
			saveRpc(repay,arrears,pact,rep0,overinterest);
			if (arrears<0){//说明还完了欠款，有结余，而且一定是要增加新的还款记录，增加新的还款期数，自动产生下一期还款
				
				
			}
			
		}else{//还完了所有期数
			pact.setAuditstate(4);			
			//System.out.println("repayOver--"+"还完了所有期数");
			pact.setCttbalance(0d);
			
			pact.setCtttaa((pact.getCttpaymonth()+pact.getCtttaa())-repay.getPayamount());
			pactDao.merge(pact);
			saveRpc(repay,0,pact,rep0,overinterest);
		}
		return redate;
		
	}
	/**
	 * 保存还款记录
	 *
	 * @param repay 还款repay对象
	 * @Arrears 当期欠款
	 * @param pact 还款的合同
	 * @param rep0  现在的还款计划
	 * @param overinterest  逾期利息
	 * 2017年9月16日 by 周喜平
	 */
	public void saveRpc(Repay repay,double Arrears,Pact pact,Repayplan rep0,double overinterest){
		Repayrec rpc=new Repayrec();
		rpc.setArrears(Arrears);
		rpc.setId(SessionUtil.getSessionString());
		rpc.setAddtime(new Timestamp(System.currentTimeMillis()));		
		rpc.setAmountmon(repay.getPayamount());//实际还款
		rpc.setPid(pact.getId());		
		rpc.setSamount(rep0.getPayamount());//应收
		rpc.setAmount(repay.getPayamount());
		rpc.setRepid(rep0.getId());
		rpc.setRepaydate(repay.getOperday());
		rpc.setInstalment(rep0.getPaytimes());
		double histcostintere[]=getHistoryCostInterest(pact.getId(),rep0.getPaytimes());
		rpc.setCost(rep0.getCost()-histcostintere[0]);			
		rpc.setInterest(rep0.getInterest()-histcostintere[1]);
		
		rpc.setOverinterest(overinterest);//暂不考虑换逾期利息			
		
		rpc.setPayarrears(0d);//因为够还，所以arrears为0或者负值
		rpc.setAmountmon(rep0.getPayamount());
		save(rpc);
	}
	/**
	 * 还的不够,实际还款< 欠款+月供
	 * 这些意味着本期是没有还完的，不能更新nextpayday，不能更新ctthpaytimes和cttspaytimes,but ctttaa
	 * 
	 *
	 * @param repay 2017年7月14日 by 周喜平
	 */
	public void repayLess(Repay repay,Pact pact){
		//还历史欠款		
		Repayrec arrearsRec=null;//历史欠款记录
		double amount=repay.getPayamount();
		boolean cttttaa=false;//表明是否刚好还完本次欠款，还是欠款不够还，两种情况下，amount都为0
		double realcost=0;//保存实际还的本金
		double overinterest=0d;
		Repayplan rep0=repayplanDao.findByPidPaytime(pact.getId(),pact.getCttspaytimes());//现在的还款计划
		Repayplan rep1=repayplanDao.findByPidPaytime(pact.getId(),pact.getCttspaytimes()+1);//下一笔还款计划
		if (pact.getCtttaa()>0){//有历史欠款			
			//System.out.println("repayLess--"+"有历史欠款");
			arrearsRec=getArrearsRec(pact.getId());
			if (arrearsRec!=null) {//开始还历史，最后amount才交给本次还款
				//System.out.println("repayLess--"+"开始还历史");
				if (amount>=arrearsRec.getArrears()){//够还本次欠款
					//System.out.println("repayLess--"+"够还本次欠款");
					arrearsRec.setPayarrears(arrearsRec.getArrears());
					Payarrearsrec prs=new Payarrearsrec();
					prs.setId(SessionUtil.getSessionString());
					prs.setAddtime(new Timestamp(System.currentTimeMillis()));
					prs.setPid(pact.getId());
					prs.setPlanid(arrearsRec.getRepid());
					prs.setRepayamount(arrearsRec.getArrears());
					payarrearsrecDAO.save(prs);//保存还欠款记录
					
					Overdue ord=overdueDAO.findbypidplanid(pact.getId(), arrearsRec.getRepid());//找到对应欠款逾期记录，修改还款
					if (ord!=null){//如果有的话						
						ord.setPayoff(ord.getAmount());//还完了
						overinterest=ord.getOverinterest();
						overdueDAO.merge(ord);
					}
					amount-=arrearsRec.getArrears();//更新结余
					cttttaa=true;
				}else{//本次不够还	
					//System.out.println("repayLess--"+"本次不够还");
					arrearsRec.setPayarrears(amount);
					Payarrearsrec prs=new Payarrearsrec();
					prs.setId(SessionUtil.getSessionString());
					prs.setAddtime(new Timestamp(System.currentTimeMillis()));
					prs.setPid(pact.getId());
					prs.setPlanid(arrearsRec.getRepid());
					prs.setRepayamount(amount);
					payarrearsrecDAO.save(prs);//保存还欠款记录
					Overdue ord=overdueDAO.findbypidplanid(pact.getId(), arrearsRec.getRepid());//找到对应欠款逾期记录，修改还款
					if (ord!=null){//如果有的话						
						ord.setPayoff(amount+ord.getPayoff());//保存累计还款
						overinterest=ord.getOverinterest();
						overdueDAO.merge(ord);
					}					
					amount=0;										
				}
				merge(arrearsRec);//更新历史还款记录，加入还额度
			}
			//欠款还完后，开始还本期,历史欠款如果没有还完，则继续还欠款，不再产生新的还款期
			double histcostintere[]=getHistoryCostInterest(pact.getId(),rep0.getPaytimes());
			//System.out.println(amount+":"+(rep0.getCost()-histcostintere[0]));
			if (amount<= 0 && !cttttaa){//欠款不够还
				//System.out.println("repayLess--"+"还欠款动作后,欠款不够还");
				double cost=0,inte=0;
				if (repay.getPayamount()>(rep0.getCost()-histcostintere[0])){
					cost=rep0.getCost()-histcostintere[0];
					inte=repay.getPayamount()-cost;
				}else{
					cost=repay.getPayamount();
					inte=0;
				}
				SaveRepay(repay, pact, cost,inte, pact.getCttspaytimes(),pact.getCtttaa()-repay.getPayamount(),overinterest);
				//还是老的欠款				
				double overdata=getSomeRepayTotal(pact.getId(),pact.getCttcate());				
				if ((overdata+repay.getPayamount())>=pact.getCttpaymonth()){//最后一期还完了
					updatePact(repay, pact,cost, pact.getCttspaytimes(),pact.getCtttaa()-repay.getPayamount());
				}else{
					updatePact(repay, pact,cost, pact.getCtthpaytimes(),pact.getCtttaa()-repay.getPayamount());
				}
				
			}else{//够还,若有结余，还应还新的期数
				//先产生还款记录				
				realcost=rep0.getCost()-histcostintere[0];				
				SaveRepay(repay, pact, realcost,rep0.getInterest()-histcostintere[1], pact.getCttspaytimes(),-amount,overinterest);
				repay.setPayamount(0);
				//System.out.println("repayLess--"+"还欠款动作后,够还，产生还款记录");
				//再还新的期数
				if (cttttaa){//可还下一笔
					//System.out.println("repayLess--"+"还欠款动作后,够还，可还下一笔");
					if (amount>=rep1.getCost()){//还下一期本金
						//System.out.println("b1");						
						SaveRepay(repay, pact, rep1.getCost(),amount-rep1.getCost(), pact.getCttspaytimes()+1,rep1.getPayamount()-amount,overinterest);						
						//产生新的欠款
						updatePact(repay, pact,rep1.getCost(), pact.getCtthpaytimes()+1,rep1.getPayamount()-amount);
					}else if(amount>0){//还本期本金，不够还，按实还为准
						//System.out.println("c1");
						SaveRepay(repay, pact, amount,0, pact.getCttspaytimes()+1,rep1.getPayamount()-amount,overinterest);
						//产生新的欠款
						updatePact(repay, pact,amount, pact.getCtthpaytimes()+1,rep1.getPayamount()-amount);						
					}else{
						updatePact(repay, pact,0, pact.getCtthpaytimes()+1,0);
					}
					
				}else{//刚好够还
					//System.out.println("repayLess--"+"还欠款动作后,够还，刚好够还");
					updatePact(repay, pact,realcost, pact.getCtthpaytimes(),0);
				}
				
			}
		}else{//无历史欠款或有结余
			//没有历史欠款也可能有逾期
			//处理逾期		
			//先判断是否有逾期
			//System.out.println("c2");
			//System.out.println("repayLess--"+"还欠款动作后,无历史欠款或有结余");
			Overdue ord=overdueDAO.findbypidplanid(pact.getId(), rep0.getId());//找到对应欠款逾期记录，修改还款			
			if (ord!=null){
				//System.out.println("repayLess--"+"还欠款动作后,无历史欠款或有结余,找到对应欠款逾期记录，修改还款");
				if ((ord.getPayoff()+amount) >ord.getAmount())
				ord.setPayoff(ord.getAmount());					
				else{
					ord.setPayoff(amount+ord.getPayoff());
				}
				overinterest=ord.getOverinterest();
				overdueDAO.merge(ord);
			}
			
			amount=amount-pact.getCtttaa();
			if (amount>=rep0.getCost()){//还本期本金，够还
				//System.out.println("repayLess--"+"还欠款动作后,无历史欠款或有结余,还本期本金");
				SaveRepay(repay, pact, rep0.getCost(),amount-rep0.getCost(), pact.getCttspaytimes(),pact.getCttpaymonth()+pact.getCtttaa()-repay.getPayamount(),overinterest);
				updatePact(repay, pact,rep0.getCost(), pact.getCtthpaytimes(),pact.getCttpaymonth()+pact.getCtttaa()-repay.getPayamount());
			}else{//还本期本金，不够还，按实还为准
				//System.out.println("repayLess--"+"还欠款动作后,无历史欠款或有结余,还本期本金，不够还，按实还为准");
				SaveRepay(repay, pact, amount,0, pact.getCttspaytimes(),pact.getCttpaymonth()+pact.getCtttaa()-repay.getPayamount(),overinterest);
				updatePact(repay, pact,amount, pact.getCtthpaytimes(),pact.getCttpaymonth()+pact.getCtttaa()-repay.getPayamount());
			}
		}
	}
	/**
	 * 保存还款记录
	 *
	 * @param repay
	 * @param pact
	 * @param amount
	 * @param intere
	 * @param times
	 * @param arrears 2017年7月16日 by 周喜平
	 */
	public void SaveRepay(Repay repay,Pact pact,double amount,double intere,int times,double arrears,double overinterest){
		try {
			Repayrec rpc=new Repayrec();//产生新的还款记录
			if (times>pact.getCttcate()){				
				pact.setAuditstate(4);	
				pactDao.merge(pact);
				return;
			}
			Repayplan rep=repayplanDao.findByPidPaytime(pact.getId(),times);//现在的还款计划
			
			rpc.setCost(amount);
			rpc.setInterest(intere);
			
			rpc.setInstalment(times);	
			rpc.setArrears(arrears);//当期的欠款，仅是当期的
			rpc.setId(SessionUtil.getSessionString());		
			rpc.setAddtime(new Timestamp(System.currentTimeMillis()));
			rpc.setAmountmon(pact.getCttpaymonth());//实际还款
			rpc.setPid(pact.getId());		
			rpc.setSamount(pact.getCttpaymonth());//应收
			rpc.setPayarrears(0d);
			
			rpc.setAmount(repay.getPayamount());
			rpc.setRepid(rep.getId());
			rpc.setRepaydate(repay.getOperday());
			rpc.setOverinterest(overinterest);//暂不考虑换逾期利息			
			save(rpc);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 更新pact
	 *
	 * @param repay
	 * @param pact
	 * @cost 本次还的本金
	 * @param times
	 * @param Ctttaa 2017年7月16日 by 周喜平
	 */
	public void updatePact(Repay repay,Pact pact,double cost,int times,double Ctttaa){
		try {								
			if (times+1>pact.getCttcate()){
				pact.setAuditstate(4);
				pactDao.merge(pact);
				return;
			}
			pact.setCtthpaytimes(times);
			pact.setCttspaytimes(times+1);
			pact.setCtttaa(Ctttaa);
			if (pact.getCttbalance()-cost<=0){
				pact.setCttbalance(0d);
			}else{
				pact.setCttbalance(pact.getCttbalance()-cost);
			}			
			pact.setNextrepayday(repayplanDao.findByPidPaytime(pact.getId(),pact.getCttspaytimes()).getPayday());
			pactDao.merge(pact);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String savePrepay(Repay repay) {
		String reState="200";//success
		try {
			Repayrec rpc=new Repayrec();//产生新的还款记录
			Pact pact=pactDao.findById(repay.getPid());
							
			pact.setAuditstate(4);	
			pactDao.merge(pact);
			
			
			rpc.setIsprepay(1);
			rpc.setCost(0d);
			rpc.setInterest(0d);			
			rpc.setInstalment(pact.getCttspaytimes());			
			rpc.setArrears(0d);//当期的欠款，仅是当期的
			rpc.setId(SessionUtil.getSessionString());		
			rpc.setAddtime(new Timestamp(System.currentTimeMillis()));
			rpc.setAmountmon(pact.getCttpaymonth());//实际还款
			rpc.setPid(pact.getId());		
			rpc.setSamount(pact.getCttpaymonth());//应收
			rpc.setPayarrears(0d);			
			rpc.setAmount(repay.getPayamount());
			
			rpc.setRepaydate(repay.getOperday());
			rpc.setOverinterest(0d);//暂不考虑换逾期利息			
			save(rpc);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			reState="250";
			throw e;
		}
		return reState;
	}
	
}