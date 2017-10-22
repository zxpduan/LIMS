package com.ioes.lims.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Favorite;
import com.ioes.lims.beans.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sias.util.Initbean;
import com.sias.util.RedisUitl;
import com.sias.util.SessionUtil;
import com.sias.util.dataTypeUtil;
import com.sias.util.dateUtils;

@Service("reportAction")
@Scope("prototype")
public class reportAction extends BaseAction<User> {
	private static Logger logger = Logger.getLogger(reportAction.class);
	public String start;// search start date
	public String end;// search end date
	String suggestparam;// suggest parameter
	
	/**
	 * @return the suggestparam
	 */
	public String getSuggestparam() {
		return suggestparam;
	}

	/**
	 * @param suggestparam the suggestparam to set
	 */
	public void setSuggestparam(String suggestparam) {
		this.suggestparam = suggestparam;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	
	/**
	 * cost interest statistic
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String costIn() {
		try {

			costIndb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "costIn";
	}
	public String costInExcel() {
		try {
			String filenameString = "贷款本息核算情况表.xls";
			filenameString = new String(filenameString.getBytes("GB2312"), "ISO_8859_1");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/msexcel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment;filename=" + filenameString);
			response.setCharacterEncoding("gb2312");
			setiDisplayLength(100000);
			costIndb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "costInExcel";
	}
	/**
	 * cost interest statistic from db
	 * 2017年7月11日 by 周喜平
	 */
	public void costIndb() {
		String orderColIndex = null;
		String orderMethod = null;
		Object orderColIndex0 = ServletActionContext.getRequest().getParameter("iSortCol_0");
		Object orderMethod0 = ServletActionContext.getRequest().getParameter("sSortDir_0");
		if (orderColIndex0 != null) {
			orderColIndex = orderColIndex0.toString();
			orderMethod = orderMethod0.toString();
		}

		if (StringUtils.isEmpty(orderColIndex)) {
			orderColIndex = "-1";
			orderMethod = "";
		}
		String order_0 = " order by t.addtime desc";
		String order_1 = " order by t.cttamount ";
		String order_2 = " order by t.cttstart ";
		String datecriteria = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		if (StringUtils.isEmpty(start)){
			start=dateUtils.getStringFromDate(new Date())+"-01";
			end=dateUtils.getEndDateString(dateFormat.format(new Date()));	
		}else{
			start+="-01";
			end=dateUtils.getEndDateString(start);
		}
		
		String sqlString = "select t.cttloandate,t2.customername,t.cttamount,t3.ratio,t.cttstart,t.cttloadenddate,t.username,t6.payday,t6.cost,t6.interest,t4.overinterest,(t6.cost+t6.interest+t4.overinterest) as total1,t6.repaydate,t6.costed,t6.interested,t4.repayinterest, (t6.costed+t6.interested+t4.repayinterest) as total2,t.cttspaytimes,t.ctthpaytimes,t.cttbalance from (select pact.*,t_0.username from pact join user t_0 on pact.operator=t_0.id where cttstart<='"+end+"') t join project t1 on t.cttproject=t1.id join customer t2 on t.cttcustomer=t2.id join ratio t3 on t.cttratio=t3.id left outer join (select t0_2.pid,ifnull(t0_1.overinterest,0) as overinterest,ifnull(t0_1.repayinterest,0) as repayinterest  from repayplan t0_2 left outer  join  overdue t0_1 on t0_1.planid=t0_2.id where t0_2.payday >='"+start+"' and t0_2.payday <='"+end+"') t4 on t.id=t4.pid left outer join (select pid ,sum(samount)as samount,sum(amount) as actual_amount from repayrec group by pid) t5 on t.id=t5.pid JOIN (select repayplan.payamount,repayplan.cost,repayplan.interest,sum(repayrec.amount) as amounted,sum(repayrec.cost) as costed ,sum(repayrec.interest) as interested, repayplan.pid,repaydate,payday  from repayplan LEFT OUTER JOIN repayrec on repayrec.repid=repayplan.id where payday>='"+start+"' and payday<='"+end+"' group by repayrec.pid,repayrec.instalment) t6 on t.id=t6.pid ";
		//String sqlString = "select t.cttloandate,t2.customername,t.cttamount,t3.ratio,t.cttstart,t.cttloadenddate,t.username,t6.payday,t.cttnowcost,t.cttnowinterest,t4.overinterest,(t.cttnowcost+t.cttnowinterest+t4.overinterest) as total1,t6.repaydate,t.cttnowcosted,t.cttnowinterested,t4.repayinterest, (t.cttnowcosted+t.cttnowinterested+t4.repayinterest) as total2,t.cttspaytimes,t.ctthpaytimes,t.cttbalance from (select pact.*,t_0.username from pact join user t_0 on pact.operator=t_0.id where cttstart<='"+end+"') t join project t1 on t.cttproject=t1.id join customer t2 on t.cttcustomer=t2.id join ratio t3 on t.cttratio=t3.id left outer join (select t0_2.pid,ifnull(t0_1.overinterest,0) as overinterest,ifnull(t0_1.repayinterest,0) as repayinterest  from repayplan t0_2 left outer  join  overdue t0_1 on t0_1.planid=t0_2.id where t0_2.payday >='"+start+"' and t0_2.payday <='"+end+"') t4 on t.id=t4.pid left outer join (select pid ,sum(samount)as samount,sum(amount) as actual_amount from repayrec group by pid) t5 on t.id=t5.pid JOIN (select repayplan.pid,repaydate,payday  from repayplan LEFT OUTER JOIN repayrec on repayrec.repid=repayplan.id where payday>='"+start+"' and payday<='"+end+"') t6 on t.id=t6.pid";
		if (orderColIndex.equals("3")) {
			order_1 += orderMethod;
			sqlString += order_1;
		} else if (orderColIndex.equals("6")) {
			order_2 += orderMethod;
			sqlString += order_2;
		} else {
			sqlString += order_0;
		}

		//System.out.println(sqlString);
		if (iDisplayLength == 0)
			iDisplayLength = 10;
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);

		List<LinkedHashMap<String, Object>> searchRs;
		

		searchRs = paginationBean.getCurrPageData(sqlString);
		
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("costIns", searchRs);
	}
	/**
	 * redirect receive unreceive statistic page
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String receiveUnreceive() {
		return "receiveUnreceive";
	}

	/**
	 * repay statistic
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String srepay() {
		try {
			srepaydb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "srepay";
	}
	/**
	 * repay statistic to excel
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String srepayExcel() {
		try {

			String filenameString = "贷款还款情况表.xls";
			filenameString = new String(filenameString.getBytes("GB2312"), "ISO_8859_1");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/msexcel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment;filename=" + filenameString);
			response.setCharacterEncoding("gb2312");
			setiDisplayLength(100000);
			srepaydb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "srepayExcel";
	}

	/**
	 * load repay statistic data from db 2017年7月11日 by 周喜平
	 */
	public void srepaydb() {
		String orderColIndex = null;
		String orderMethod = null;
		Object orderColIndex0 = ServletActionContext.getRequest().getParameter("iSortCol_0");
		Object orderMethod0 = ServletActionContext.getRequest().getParameter("sSortDir_0");
		if (orderColIndex0 != null) {
			orderColIndex = orderColIndex0.toString();
			orderMethod = orderMethod0.toString();
		}

		if (StringUtils.isEmpty(orderColIndex)) {
			orderColIndex = "-1";
			orderMethod = "";
		}
		String order_0 = " order by t.addtime desc";
		String order_1 = " order by t.cttamount ";
		String order_2 = " order by t.cttstart ";
		String datecriteria = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		if (StringUtils.isEmpty(start)){
			start=dateUtils.getStringFromDate(new Date())+"-01";
			end=dateUtils.getEndDateString(dateFormat.format(new Date()));	
		}else{
			start+="-01";
			end=dateUtils.getEndDateString(start);
		}
		//String sqlString = "select t.id,t.cttcode,t1.pname,t2.customername,t.cttamount,t3.ratio,t.cttcate,t.cttstart,t.cttloadenddate,t6.payday,t.cttpaymonth,t6.repaydate,t5.samount,t5.actual_amount,t4.overdue_times,t.username from (select pact.*,t_0.username from pact join user t_0 on pact.operator=t_0.id where cttstart<='"+end+"') t join project t1 on t.cttproject=t1.id join customer t2 on t.cttcustomer=t2.id join ratio t3 on t.cttratio=t3.id left outer join (select pid,count(*) as overdue_times from overdue GROUP BY pid) t4 on t.id=t4.pid left outer join (select pid ,sum(samount)as samount,sum(amount) as actual_amount from repayrec group by pid) t5 on t.id=t5.pid LEFT OUTER JOIN (select repayplan.pid,repaydate,payday  from  repayplan  LEFT OUTER JOIN repayrec  on repayrec.repid=repayplan.id where payday>='"+start+"' and payday<='"+end+"') t6 on t.id=t6.pid";
		String sqlString =		
		"select t.id,t.cttcode,t1.pname,t2.customername,t.cttamount,t3.ratio,t.cttcate,t.cttstart,t.cttloadenddate,t6.payday,t.cttpaymonth,t6.repaydate,t5.samount,t5.actual_amount,t4.overdue_times,t.username"
		+ "	from (select pact.*,t_0.username from pact join user t_0 on pact.operator=t_0.id where cttstart<='"+end+"') t "
		+ "join project t1 on t.cttproject=t1.id "
		+ "join customer t2 on t.cttcustomer=t2.id "
		+ "join ratio t3 on t.cttratio=t3.id "
		+ "left outer join (select pid,count(*) as overdue_times from overdue GROUP BY pid) t4 on t.id=t4.pid "
		+ "left outer join (select pid,samount,actual_amount from (select pid,sum(payamount) as samount  from repayplan where payday <='"+end+"' GROUP BY pid) t_samount join (select id ,cttpaymonth*ctthpaytimes-ctttaa as actual_amount from pact where auditstate=2) t_actual on t_samount.pid=t_actual.id) t5 on t.id=t5.pid "
		+ "JOIN (select repayplan.pid,repaydate,payday  from  repayplan  LEFT OUTER JOIN repayrec  on repayrec.repid=repayplan.id where payday>='"+start+"' and payday<='"+end+"') t6 on t.id=t6.pid ";

		if (orderColIndex.equals("3")) {
			order_1 += orderMethod;
			sqlString += order_1;
		} else if (orderColIndex.equals("6")) {
			order_2 += orderMethod;
			sqlString += order_2;
		} else {
			sqlString += order_0;
		}

		//System.out.println(sqlString);
		if (iDisplayLength == 0)
			iDisplayLength = 10;
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);

		List<LinkedHashMap<String, Object>> searchRs;
		List<LinkedHashMap<String, Object>> searchRs0=new ArrayList<LinkedHashMap<String,Object>>();

		searchRs = paginationBean.getCurrPageData(sqlString);
		for (LinkedHashMap<String, Object> map : searchRs) {
			if (map.get("PAYDAY")!=null&&map.get("REPAYDATE")!=null){
				if (StringUtils.isNotEmpty(map.get("PAYDAY").toString())&&StringUtils.isNotEmpty(map.get("REPAYDATE").toString())){
					map.put("DIFFDAYS", dateUtils.getDiffDays(map.get("PAYDAY").toString(), map.get("REPAYDATE").toString()));
				}else{
					map.put("DIFFDAYS", "");
				}
			}else{
				map.put("DIFFDAYS", "");
			}
			searchRs0.add(map);
			//map.get("REPAYDATE");
			
		}
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("srepays", searchRs0);
	}

	/**
	 * load ajax data about receive unreceive statistic
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String reUre() {
		try {

			reUredb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "reUre";
	}

	/**
	 * export excel about receive and unreceive
	 *
	 * @return
	 * @throws Exception
	 *             2017年7月11日 by 周喜平
	 */
	public String reUreExcel() throws Exception {
		try {

			String filenameString = "贷款应收未收情况表.xls";
			filenameString = new String(filenameString.getBytes("GB2312"), "ISO_8859_1");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/msexcel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment;filename=" + filenameString);
			response.setCharacterEncoding("gb2312");
			setiDisplayLength(100000);
			reUredb();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "reUreExcel";

	}

	/**
	 * load data from db about receive unreceive statistic 2017年7月11日 by 周喜平
	 */
	public void reUredb() {
		String orderColIndex = null;
		String orderMethod = null;
		Object orderColIndex0 = ServletActionContext.getRequest().getParameter("iSortCol_0");
		Object orderMethod0 = ServletActionContext.getRequest().getParameter("sSortDir_0");
		if (orderColIndex0 != null) {
			orderColIndex = orderColIndex0.toString();
			orderMethod = orderMethod0.toString();
		}

		if (StringUtils.isEmpty(orderColIndex)) {
			orderColIndex = "-1";
			orderMethod = "";
		}
		String order_0 = " order by t5.addtime desc";
		String order_1 = " order by t5.cttamount ";
		String order_2 = " order by t5.cttstart ";
		String datecriteria = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = dateFormat.format(new Date());
		String sqlString ="select t5.cttcode,t6.pname,t5.customername,t7.ratio,t5.cttamount,t5.cttcate,t5.cttstart,t5.cttloadenddate,t5.cttrepayday,t5.cttpaymonth,t4.arrearscount,t4.hisarrears,t5.total,t5.username,t4.overinterest,t4.overtimes,t5.customermobile FROM"
				+ "(select t.*,IFNULL(t1.arrearscount,0) as arrearscount  from (select pid,count(pid) as overtimes,sum(amount)-sum(payoff) as hisarrears,sum(overinterest) as overinterest from overdue GROUP BY pid) t LEFT OUTER JOIN (select pid,count(*) as arrearscount from overdue where amount<>payoff group by pid) t1 on t.pid=t1.pid"
				+ "	) t4 join ("
				+ "select t2.total,t3.* from (select pid,sum(payamount) total from repayplan where payday<='"+nowdate+"' GROUP BY pid) t2 join (select t_0.*,t_1.customername,t_1.customermobile,t_2.username from (SELECT * from pact where auditstate=2) t_0 join customer t_1 on t_0.cttcustomer=t_1.id join user t_2 on t_0.operator=t_2.id) t3 on t3.id=t2.pid"
				+ ") t5 on t4.pid=t5.id "
				+ " join project t6 on t5.cttproject=t6.id "
				+ " join ratio t7 on t5.cttratio=t7.id "; 
		if (StringUtils.isNotEmpty(end)) {
			//System.out.println(end);
			sqlString ="select t5.cttcode,t6.pname,t5.customername,t7.ratio,t5.cttamount,t5.cttcate,t5.cttstart,t5.cttloadenddate,t5.cttrepayday,t5.cttpaymonth,t4.arrearscount,t4.hisarrears,t5.total,t5.username,t4.overinterest,t4.overtimes,t5.customermobile FROM"
					+ "(select t.*,IFNULL(t1.arrearscount,0) as arrearscount  from (select pid,count(pid) as overtimes,sum(amount)-sum(payoff) as hisarrears,sum(overinterest) as overinterest from overdue GROUP BY pid) t LEFT OUTER JOIN (select pid,count(*) as arrearscount from overdue where amount<>payoff group by pid) t1 on t.pid=t1.pid"
					+ "	) t4 join ("
					+ "select t2.total,t3.* from (select pid,sum(payamount) total from repayplan where payday<='"+end+"' GROUP BY pid) t2 join (select t_0.*,t_1.customername,t_1.customermobile,t_2.username from (SELECT * from pact where auditstate=2) t_0 join customer t_1 on t_0.cttcustomer=t_1.id join user t_2 on t_0.operator=t_2.id) t3 on t3.id=t2.pid"
					+ ") t5 on t4.pid=t5.id "
					+ " join project t6 on t5.cttproject=t6.id"
					+ " join ratio t7 on t5.cttratio=t7.id "; 
		}
		
		
		
		if (orderColIndex.equals("3")) {
			order_1 += orderMethod;
			sqlString += order_1;
		} else if (orderColIndex.equals("6")) {
			order_2 += orderMethod;
			sqlString += order_2;
		} else {
			sqlString += order_0;
		}
		//System.out.println(sqlString);
		if (iDisplayLength == 0)
			iDisplayLength = 10;
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);

		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("reUres", searchRs);
	}

	/**
	 * redirect to ledger page
	 *
	 * @return 2017年7月10日 by 周喜平
	 */
	public String ledger() {
		return "ledger";
	}

	public String ledgerdata() {
		try {
			ledgerdb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ledgerdata";
	}

	/**
	 * load ledger data from db 2017年7月11日 by 周喜平
	 */
	public void ledgerdb() {
		String orderColIndex = null;
		String orderMethod = null;
		Object orderColIndex0 = ServletActionContext.getRequest().getParameter("iSortCol_0");
		Object orderMethod0 = ServletActionContext.getRequest().getParameter("sSortDir_0");
		if (orderColIndex0 != null) {
			orderColIndex = orderColIndex0.toString();
			orderMethod = orderMethod0.toString();
		}

		if (StringUtils.isEmpty(orderColIndex)) {
			orderColIndex = "-1";
			orderMethod = "";
		}
		String order_0 = " order by t.addtime desc";
		String order_1 = " order by t.cttamount ";
		String order_2 = " order by t.cttstart ";
		String datecriteria = "";
		String sqlString = "select t.cttcode,t1.customername,t.cttamount,t2.ratio,t.cttcate,t.cttstart,t.payday,t1.customermobile,t3.username,t.cttbalance from (select t_0.*,t_1.payday from pact t_0 join repayplan t_1 on t_0.id=t_1.pid where t_0.cttcate=t_1.paytimes) t join customer t1 on t.cttcustomer=t1.id join ratio t2 on t.cttratio=t2.id join user t3 on t.operator=t3.id ";
		if (StringUtils.isNotEmpty(start)) {
			sqlString = "select t.cttcode,t1.customername,t.cttamount,t2.ratio,t.cttcate,t.cttstart,t.payday,t1.customermobile,t3.username,t.cttbalance from (select t_0.*,t_1.payday from pact t_0 join repayplan t_1 on t_0.id=t_1.pid where t_0.cttcate=t_1.paytimes) t join customer t1 on t.cttcustomer=t1.id join ratio t2 on t.cttratio=t2.id join user t3 on t.operator=t3.id  where t.cttstart >='"
					+ start + "' and t.cttstart<='" + end + "' ";
		}

		if (orderColIndex.equals("3")) {
			order_1 += orderMethod;
			sqlString += order_1;
		} else if (orderColIndex.equals("6")) {
			order_2 += orderMethod;
			sqlString += order_2;
		} else {
			sqlString += order_0;
		}

		
		if (iDisplayLength == 0)
			iDisplayLength = 10;
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);

		List<LinkedHashMap<String, Object>> searchRs;
		
		searchRs = paginationBean.getCurrPageData(sqlString);
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("ledgers", searchRs);
	}

	/**
	 * export excel about ledger
	 *
	 * @return
	 * @throws Exception
	 *             2017年7月11日 by 周喜平
	 */
	public String ledgerExcel() throws Exception {
		try {
			String filenameString = "贷款台账.xls";
			filenameString = new String(filenameString.getBytes("GB2312"), "ISO_8859_1");
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/msexcel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment;filename=" + filenameString);
			response.setCharacterEncoding("gb2312");
			setiDisplayLength(100000);
			ledgerdb();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ledgerExcel";

	}
	/**
	 * integrated query
	 *
	 * @return 2017年7月11日 by 周喜平
	 */
	public String integratedQ() {

		/*
		 * Enumeration<String> aEnumeration = ServletActionContext.getRequest().getParameterNames(); while (aEnumeration.hasMoreElements()) {
		 * System.out.println(aEnumeration.nextElement()+":"+ServletActionContext.getRequest().getParameter(aEnumeration.nextElement()).toString()); }
		 */
		String orderColIndex = ServletActionContext.getRequest().getParameter("iSortCol_0").toString();
		String orderMethod = ServletActionContext.getRequest().getParameter("sSortDir_0").toString();
		// System.out.println(orderColIndex+":"+orderMethod);
		String order_0 = " order by t1.addtime desc";
		String order_1 = " order by t1.cttcode ";
		String order_2 = " order by t1.cttcustomer ";
		if (suggestparam == null)
			suggestparam = "";
		String sqlString = "select t1.*,t3.username,t5.ratio,t4.pname from (select pact.*,customer.customername,customer.customerreplace1,customer.customerreplace2 from pact join customer on pact.cttcustomer=customer.id where cttcode like '%" + suggestparam + "%' or customername like '%" + suggestparam + "%' or customerreplace1 like '%" + suggestparam + "%' or customerreplace2 like '%" + suggestparam + "%') t1 join user t3 on t1.operator=t3.id join project t4 on t1.cttproject=t4.id join ratio t5 on t1.cttratio=t5.id";
		
		if (orderColIndex.equals("1")) {
			order_1 += orderMethod;
			sqlString += order_1;
		} else if (orderColIndex.equals("2")) {
			order_2 += orderMethod;
			sqlString += order_2;
		} else {
			sqlString += order_0;
		}
		//System.out.println(sqlString);
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);

		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("pacts", searchRs);
		suggestparam = "";// delete cache search text last time
		return "integratedQ";
	}
}
