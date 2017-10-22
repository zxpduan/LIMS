package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.Session;
import org.hibernate.hql.ast.SqlASTFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Pact;
import com.ioes.lims.beans.Project;
import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.Repay;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;
import com.sias.util.dateUtils;

@Service("repayAction")
@Scope("prototype")
public class repayAction extends BaseAction<Repay> {
	private static final long serialVersionUID = -3447248666260840079L;
	public String opres;
	List<Repay> repay;	
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
	 * @return the repay
	 */
	public List<Repay> getRepay() {
		return repay;
	}

	/**
	 * @param repay the repay to set
	 */
	public void setRepay(List<Repay> repay) {
		this.repay = repay;
	}

	/**
	 * @return the opres
	 */
	public String getOpres() {
		return opres;
	}

	/**
	 * @param opres
	 *            the opres to set
	 */
	public void setOpres(String opres) {
		this.opres = opres;
	}

	public String execute() {
		return "test";
	}
	/**
	 * batch repay
	 *
	 * @return 2017年7月14日 by 周喜平
	 */
	public String saveRepay() {		
		if (repay!=null){
			repayrecDao.saveAll(repay);			
		}
		setOpres("200");
		return "operateRes";
	}
	/**
	 * repay a pact at once
	 *
	 * @return 2017年7月14日 by 周喜平
	 */
	public String saveSingleRepay() {		
		
		repayrecDao.saveSingle(model);
		setOpres("200");
		return "operateRes";
	}	
	/**
	 * pay off,贷款提前还完
	 * delids was reused,delids was been use in delete ,and used here
	 * @return 2017年7月11日 by 周喜平
	 */
	public String doprepayment() {
		setOpres("{\"state\":\""+repayrecDao.savePrepay(model)+"\"}");
		return "operateRes";
	}

	public String tableData() {
		/*
		 * Enumeration<String> aEnumeration = ServletActionContext.getRequest().getParameterNames(); while (aEnumeration.hasMoreElements()) {
		 * System.out.println(aEnumeration.nextElement()+":"+ServletActionContext.getRequest().getParameter(aEnumeration.nextElement()).toString()); }
		 */
		//String orderColIndex = ServletActionContext.getRequest().getParameter("iSortCol_0").toString();
		//String orderMethod = ServletActionContext.getRequest().getParameter("sSortDir_0").toString();		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sqlString="";
		if (model.getRepayday()==null){
			model.setRepayday(new Date());
		}
		if (model.getOperday()==null){
			model.setOperday(new Date());
		}
		String repd=dateFormat.format(model.getRepayday());
		String opd=dateFormat.format(model.getOperday());
		sqlString="select t.cttcode,t.id,t.cttbalance,t.ctttaa,t3.customername,t2.paytimes,t2.payday,t2.cost,t2.payamount,t2.interest from  (select * from pact where auditstate=2 and  nextrepayday='"+repd+"') t join (select * from repayplan where payday='"+repd+"') t2 on t.id=t2.pid join customer t3 on t.cttcustomer=t3.id order by t2.paytimes";
		if (model.getPactcode()!=null&& !model.getPactcode().trim().equalsIgnoreCase("")){
			sqlString="select t.cttcode,t.id,t.cttbalance,t.ctttaa,t3.customername,t2.paytimes,t2.payday,t2.cost,t2.payamount,t2.interest from  (select * from pact where cttcode='"+model.getPactcode()+"' and auditstate=2 and  nextrepayday='"+repd+"') t join (select * from repayplan where payday='"+repd+"') t2 on t.id=t2.pid join customer t3 on t.cttcustomer=t3.id where t3.customername ='"+model.getCustomername()+"' or t3.customerreplace1='"+model.getCustomername()+"' or t3.customerreplace2='"+model.getCustomername()+"' order by t2.paytimes";
		}
		
		
		
		
		//System.out.println(sqlString);
		
		paginationBean.setPageSize(1000);		
		paginationBean.setCurrPage(1);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);		
		request.put("pacts", searchRs);
		request.put("opday", opd);	
		request.put("repd", repd);	
		
		return "tableData";
	}
	public String repayRegist() {

		return "repayRegist";
	}
	/**
	 * repay
	 *
	 * @return 2017年7月14日 by 周喜平
	 */
	public String repay(){
		return "repay";
	}
	/**
	 * redirect to repayrec page
	 *
	 * @return 2017年7月6日 by 周喜平
	 */
	public String repayrec(){
		return "repayrec";
	}
	/**
	 * redirect to prepayment page
	 *
	 * @return 2017年7月6日 by 周喜平
	 */
	public String prepayment(){
		return "prepayment";
	}
	/**
	 * repay rec table
	 *
	 * @return 2017年7月6日 by 周喜平
	 */
	public String ajaxdata4() {
	
		if (suggestparam == null)
			suggestparam = "";
		String sqlString = "select t0.cttcode,t.samount,t.amount,t.cost,t.interest,t.overinterest,t.arrears,t.instalment,t1.payday, t.repaydate,t.isprepay from (select * from pact where id='"+suggestparam+"') t0 join (select * from repayrec where pid='"+suggestparam+"') t on t0.id=t.pid join (select * from repayplan where pid='"+suggestparam+"') t1 on t0.id=t1.pid where t.instalment=t1.paytimes ORDER BY t.instalment,t.addtime";
		
		
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;
		//System.out.println(sqlString);
		searchRs = paginationBean.getCurrPageData(sqlString);

		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("pacts", searchRs);
		suggestparam = "";// delete cache search text last time
		return "ajaxdata4";
	}
	/**
	 * 
	 * look overdue interest
	 * redirect to overdue
	 * @return 2017年7月7日 by 周喜平
	 */
	public String overdue(){
		return "overdue";
	}
	/**
	 * overdue table
	 *
	 * @return 2017年7月6日 by 周喜平
	 */
	public String ajaxdata5() {

		
		if (suggestparam == null)
			suggestparam = "";
		String sqlString = "select t0.cttcode,t1.paytimes,t.startdate,t.amount,t.overday,t.overinterest from (select * from pact where id='"+suggestparam+"') t0 join (select * from overdue where pid='"+suggestparam+"') t on t0.id=t.pid join (select * from repayplan where pid='"+suggestparam+"') t1 on t0.id=t1.pid where t.planid=t1.id order by  startdate";
		
		//System.out.println(suggestparam);
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
		return "ajaxdata5";
	}
	public String ajaxdata6() {
		String orderColIndex = ServletActionContext.getRequest().getParameter("iSortCol_0").toString();
		String orderMethod = ServletActionContext.getRequest().getParameter("sSortDir_0").toString();
		// System.out.println(orderColIndex+":"+orderMethod);
		String order_0 = " order by t1.addtime desc";
		String order_1 = " order by t1.cttcode ";
		String order_2 = " order by t1.cttcustomer ";
		if (suggestparam == null)
			suggestparam = "";
		String sqlString = "select t1.*,t3.username from (select PACT.*,CUSTOMER.customername from pact join customer on pact.cttcustomer=customer.id where  cttcode like '%" + suggestparam + "%' OR customername like '%" + suggestparam + "%') t1  join user t3 on t1.operator=t3.id ";
		//System.out.println(sqlString);
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
		return "ajaxdata6";
	}
}
