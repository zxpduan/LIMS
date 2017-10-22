package com.ioes.lims.task;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.enterprise.inject.New;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONObject;

import com.ioes.lims.beans.Overdue;
import com.ioes.lims.beans.Pact;
import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.Repayrec;
import com.ioes.lims.beans.Sysparam;
import com.ioes.lims.daoimp.RepayrecDAO;
import com.ioes.lims.idao.IOverdueDAO;
import com.ioes.lims.idao.IPactDAO;
import com.ioes.lims.idao.IRatioDAO;
import com.ioes.lims.idao.IRepayrecDAO;
import com.ioes.lims.idao.ISysparamDAO;
import com.ioes.lims.utils.CalcuInterest;
import com.sias.util.SessionUtil;
import com.sias.util.dateUtils;

/**
 * 读取下发的控制指令，周期执行，发现有新的指令开始下发
 * 
 * @author 周喜平 2015-10-15
 */
public class ReadOrderTask extends Observable implements Runnable{
	private static final Log log = LogFactory.getLog(ReadOrderTask.class);
	ScheduledExecutorService Service;
	ServletContext sc;
	IRepayrecDAO repayrecDAO;
	IOverdueDAO overdueDAO;
	IRatioDAO ratioDAO;
	IPactDAO pactDAO;
	ISysparamDAO sysparamDAO;
	long interval=2;
	String attime="0:10:01";//任务开始时间
	String[] times=null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
	DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	boolean multitime=false;
	LinkedHashMap<String, Double> ratiomap=null;
	int overday=5;
	/**
	 * @return the attime
	 */
	public String getAttime() {
		return attime;
	}

	/**
	 * @param attime the attime to set
	 */
	public void setAttime(String attime) {
		this.attime = attime;
		times=this.attime.split(";");
	}

	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
		
	}

	public ReadOrderTask(long interval,ServletContext sc) {
		this.interval=interval;
		this.sc=sc;
		
	}
	/**
	 * 通知观察者，重启任务
	 * 2017年7月11日 by 周喜平
	 */
	public void doBusiness() {
		super.setChanged();
		notifyObservers();
	}
	/**
	 * 开始任务
	 * 采用了scheduleAtFixedRate
	 * 2017年7月11日 by 周喜平
	 */
	public void start(){
		try{
			log.info("Start scan task....");
			getLockOrderDao();
			//System.out.println("Start scan task....");
			Service= Executors.newSingleThreadScheduledExecutor();
			Service.scheduleAtFixedRate(this, 10, interval, TimeUnit.SECONDS);
			
		}catch(Exception e){
			e.printStackTrace();
			doBusiness();
		}
	}
	/**
	 * 必须实现runnable，才能满足scheduleAtFixedRate周期执行任务
	 */
	public void run() {
		try {
			doTask();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * 执行的周期性任务
	 * task one is find overdue repay month
	 * tast two is modify overdue interest in overdue table 
	 * 2017年7月11日 by 周喜平
	 */
	public void doTask(){
		try {
			log.info("do task....");
			log.info("Now local time is "+dateFormat.format(new Date())+",the timing is "+dateFormat.format(dateFormat.parse(attime)));
			//System.out.println("Now local time is "+dateFormat.format(new Date())+",the timing is "+dateFormat.format(dateFormat.parse(attime)));
			if (times.length>1){
				for (String string : times) {
					
					if (dateFormat.format(dateFormat.parse(string)).equals(dateFormat.format(new Date()))){//该时间点有定时要求
						multitime=true;
						break;
					}					
				}
				
				if (multitime){
					//System.out.println("starting!!!!!!!!!!!!!!!!!!!!!!!!");
					doEvent1();
					doEvent2();
					multitime=false;
				}
				
			}else{
				if (dateFormat.format(dateFormat.parse(attime)).equals(dateFormat.format(new Date()))){
					//System.out.println("starting!!!!!!!!!!!!!!!!!!!!!!!!");
					doEvent1();
					doEvent2();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("readOrdertask.java error", e.getCause());
			doBusiness();
		}
	}
	/**
	 * find overdue pay month，into overdue table
	 * 2017年7月12日 by 周喜平
	 */
	public void doEvent1() throws Exception{
		List <LinkedHashMap<String, Object>> res=repayrecDAO.findBeOver();		
		for (LinkedHashMap<String, Object> map : res) {
			
			if (map.get("OVERDAY")==null){//说明还没有逾期记录
				Overdue o=new Overdue();
				o.setId(SessionUtil.getSessionString());
				o.setPid(map.get("PID").toString());
				o.setPlanid(map.get("PLANID").toString());
				
				o.setAmount((map.get("PER_ARREARS")==null)?0d:Double.parseDouble(map.get("PER_ARREARS").toString()));
				
				o.setStartdate(dateFormat2.parse(map.get("PAYDAY").toString()));
				o.setOverinterest(0d);
				o.setAddtime(new Timestamp(System.currentTimeMillis()));
				o.setOverday((int)dateUtils.getDiffDays(map.get("PAYDAY").toString(), dateFormat2.format(new Date())));
				o.setPayoff(0d);
				o.setUpdatedate(new Timestamp(System.currentTimeMillis()));
				o.setRepayinterest(0d);
				overdueDAO.save(o);	
			}else if(StringUtils.isEmpty(map.get("OVERDAY").toString())){//说明还没有逾期记录
				Overdue o=new Overdue();
				o.setId(SessionUtil.getSessionString());
				o.setPid(map.get("PID").toString());
				o.setPlanid(map.get("PLANID").toString());
				o.setAmount((map.get("PER_ARREARS")==null)?0d:Double.parseDouble(map.get("PER_ARREARS").toString()));				
				o.setStartdate(dateFormat2.parse(map.get("PAYDAY").toString()));
				o.setOverinterest(0d);
				o.setAddtime(new Timestamp(System.currentTimeMillis()));
				o.setUpdatedate(new Timestamp(System.currentTimeMillis()));
				o.setOverday((int)dateUtils.getDiffDays(map.get("PAYDAY").toString(), dateFormat2.format(new Date())));
				o.setPayoff(0d);
				o.setRepayinterest(0d);
				overdueDAO.save(o);	
			}
		}
	}
	/**
	 * calcullate overdue interest and update overdue
	 * 2017年7月11日 by 周喜平
	 */
	public void doEvent2(){
		List <Overdue> res=overdueDAO.findArrearsRecs();
		List<Ratio> ratios=ratioDAO.findAll();
		Pact p=null;
		ratiomap=new LinkedHashMap<String, Double>();
		for (Ratio ratio : ratios) {
			ratiomap.put(ratio.getId(), ratio.getRatio());
		}
		for (Overdue overdue : res) {
			p=pactDAO.findById(overdue.getPid());
			//新增逾期天数，算出新增利息
			int overdays=(int)dateUtils.getDiffDays(dateFormat2.format(overdue.getStartdate()), dateFormat2.format(new Date()))-overdue.getOverday();
			int isSameDay=(int)dateUtils.getDiffDays(dateFormat2.format(overdue.getUpdatedate()), dateFormat2.format(new Date()));
			
			Sysparam sp=sysparamDAO.findOnlyOne();
			if (sp!=null) overday=sp.getOverdueday();
			if (isSameDay!=0){//==0,已经更新,每更新一次，就会修改updatedate
				if (overdays+overdue.getOverday()>overday){	//超过逾期天数才开始计息				
					overdue.setOverinterest(overdue.getOverinterest()+CalcuInterest.calOverInteresst(overdue.getAmount()-overdue.getPayoff(), overdays, (double)ratiomap.get(p.getCttratio())));	
				}				
				overdue.setOverday(overdays+overdue.getOverday());
				overdue.setUpdatedate(new Timestamp(System.currentTimeMillis()));
			}
			
			overdueDAO.merge(overdue);
		}
	}
	/**
	 * 获得spring注入的dao
	 * 2017年7月11日 by 周喜平
	 */
	public void getLockOrderDao(){
		// 读取spring配置文件
		//ClassPathResource classPathResource = new ClassPathResource("applicationContext.xml");
		//beanFactory = new XmlBeanFactory(classPathResource);
		//this.lockorderDao = (LockorderDao)beanFactory.getBean("lockorderDao");F
		
		WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(this.sc);
		this.repayrecDAO = (IRepayrecDAO)ctx.getBean("repayrecDAO");
		this.overdueDAO = (IOverdueDAO)ctx.getBean("overdueDAO");
		this.ratioDAO=(IRatioDAO)ctx.getBean("ratioDAO");
		this.pactDAO=(IPactDAO)ctx.getBean("pactDAO");
		this.sysparamDAO=(ISysparamDAO)ctx.getBean("sysparamDAO");
	}
}
