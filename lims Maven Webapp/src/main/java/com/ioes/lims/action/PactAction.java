package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Pact;
import com.ioes.lims.beans.Project;
import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.Repayplan;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("pactAction")
@Scope("prototype")
public class PactAction extends BaseAction<Pact> {
	private static final long serialVersionUID = -3447248666260840079L;
	public String opres;
	
	public String delids;// ids that will be deleted
	String suggestparam;// suggest parameter
	String optype;// sign modify repay man or modify loan day
	String ctt_serail;//indicant of serial is auto created, or maunal created,not 0 is auto,0 is manual
	

	/**
	 * @return the ctt_serail
	 */
	public String getCtt_serail() {
		return ctt_serail;
	}

	/**
	 * @param ctt_serail the ctt_serail to set
	 */
	public void setCtt_serail(String ctt_serail) {
		this.ctt_serail = ctt_serail;
	}

	/**
	 * @return the optype
	 */
	public String getOptype() {
		return optype;
	}

	/**
	 * @param optype
	 *            the optype to set
	 */
	public void setOptype(String optype) {
		this.optype = optype;
	}

	/**
	 * @return the suggestparam
	 */
	public String getSuggestparam() {
		return suggestparam;
	}

	/**
	 * @param suggestparam
	 *            the suggestparam to set
	 */
	public void setSuggestparam(String suggestparam) {
		this.suggestparam = suggestparam;
		/*try {
			if (suggestparam!=null){
				this.suggestparam=new String(suggestparam.getBytes("iso-8859-1"),"utf-8");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * @return the delids
	 */
	public String getDelids() {
		return delids;
	}

	/**
	 * @param delids
	 *            the delids to set
	 */
	public void setDelids(String delids) {
		this.delids = delids;
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
	 * delete pact,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String delpact() {		
		Pact pact=null;
		String[] objs=splitIDS(delids);
		if (!isAdmin())
		for (String string : objs) {
			
			pact=pactDao.findById(string);
			if (!pact.getOperator().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!ispactOtherPrivilege()){				
				setOpres("{\"state\":\"310\"}");
				
				return "operateRes";
			}
			
			if (pact.getAuditstate()==2||pact.getAuditstate()==4){				
				setOpres("{\"state\":\"300\"}");
				
				return "operateRes";
			}
			
		}
		pactDao.delBatch(splitIDS(delids));
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}
	
	/**
	 * redirect to edit pact page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String editpact() {
		Object ot = ServletActionContext.getRequest().getParameter("optype");
		Pact pact = pactDao.findById(model.getId());
		if (!pact.getOperator().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!ispactOtherPrivilege()){
			return "noprivilige";
		}
		Project project = projectDao.findById(pact.getCttproject());
		Customer c = customerDao.findById(pact.getCttcustomer());
		List<Ratio> rs = ratioDao.findAll();
		request.put("editratios", rs);
		request.put("editpact", pact);
		request.put("editproject", project);
		if (ot != null) {
			optype = ot.toString();
		} else {
			optype = "";
		}
		request.put("optype", optype);
		request.put("editcustomer", c);

		return "editpact";
	}

	/**
	 * entering contract
	 *
	 * @return 2017年6月23日 by 周喜平
	 */
	public String entering() {
		List<Ratio> rs = ratioDao.findAll();
		request.put("editratios", rs);
		return "entering";
	}

	/**
	 * redirect to examing pact
	 *
	 * @return 2017年6月25日 by 周喜平
	 */
	public String pactaudit() {
		
		Pact u = pactDao.findById(model.getId());
		if (!u.getOperator().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!ispactOtherPrivilege()){
			return "noprivilige";
		}
		if (u.getAuditstate() == null || u.getAuditstate() == 0) {
			u.setAuditstate(1);
		}
		pactDao.merge(u);
		request.put("editpact", u);
		request.put("ratio", ratioDao.findById(u.getCttratio()).getRatio());
		return "pactaudit";
	}
	/**
	 * do audit event
	 *
	 * @return 2017年6月29日 by 周喜平
	 */
	public String audit() {
		String re="200";
		Pact u = pactDao.findById(model.getId());
		if (model.getAuditstate()==3){//Not pass
			if (u.getAuditstate()==2&&(!isAdmin())){//审核通过后，不允许再次不通过
				re= "330";
				setOpres(re);
				return "operateRes";
			}else{
				u.setAuditstate(model.getAuditstate());//set 3 to audit state
				re= pactDao.merge(u);
				setOpres(re);
				return "operateRes";
			}
			
		}
		if (u.getAuditstate()==2){//has  passed
			re="320";//不能重复审核通过
			setOpres(re);
			return "operateRes";
		}
		//check cost
		if (u.getCttamount()==null||u.getCttamount()<=0){
			re="310";
			setOpres(re);
			return "operateRes";
		}
		//check ratio
		if (u.getCttratio()==null||u.getCttratio()==""){
			re="310";
			setOpres(re);
			return "operateRes";
		}		
		//check imstalment
		if (u.getCttcate()==null||u.getCttcate()<=0){
			re="310";
			setOpres(re);
			return "operateRes";
		}
		
		u.setAuditstate(model.getAuditstate());//set 2 to audit state 设置状态为2，通过审核
		re= pactDao.updateAuditPact(u);
		setOpres(re);
		return "operateRes";
	}

	/**
	 * get the max serial about a same project
	 *
	 * @return 2017年6月23日 by 周喜平
	 */
	public String maxserial() {
		int serial = pactDao.findMaxSerial(model);
		setOpres("{\"state\":\"200\",\"serail\":\"" + serial + "\"}");
		return "operateRes";
	}

	/**
	 * edit pact information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditpact() {
		setOpres("250");
		try {
			Pact p=pactDao.findById(model.getId());
			//正在审核，审核已通过，已还完，且非超级用户不能修改，审核不通过或者未审核可以修改
			if (p.getAuditstate()!=null&&(p.getAuditstate()==2||p.getAuditstate()==1||p.getAuditstate()==4)&&(!isAdmin())){
				setOpres("310");
				return "operateRes";
			}
			
			model.setAddtime(new Timestamp(System.currentTimeMillis()));
			if (model.getCttdiscount() == null)
			p.setCttdiscount(0);
			p.setCtttaa(model.getCtttaa());
			/**
			 * 非超级用户在审核不通过或者未审核的情况下，可以修改合同，修改后，设置状态为未审核，
			 * 若是超级用户，则在1,2,4状态下可以修改，修改完后状态不变
			 */
			if (p.getAuditstate()==0||p.getAuditstate()==3){
				p.setAuditstate(0);
			}
			p.setCtthpaytimes(model.getCtthpaytimes());
			p.setCttdate(model.getCttdate());
			p.setCttproject(model.getCttproject());
			p.setCttamount(model.getCttamount());
			p.setCttamountu(model.getCttamountu());
			p.setCttserial(model.getCttserial());
			p.setCttcode(model.getCttcode());
			p.setCttcustomer(model.getCttcustomer());
			p.setCttbank(model.getCttbank());
			p.setCttbankcard(model.getCttbankcard());
			p.setCttratio(model.getCttratio());
			p.setCttcate(model.getCttcate());
			p.setCttrepaycate(model.getCttrepaycate());
			p.setCttfun(model.getCttfun());
			p.setCttprice(model.getCttprice());
			p.setCttselfprice(model.getCttselfprice());
			p.setCttstart(model.getCttstart());
			p.setCttloandate(model.getCttloandate());
			p.setCttrepayday(model.getCttrepayday());
			p.setCtthouseloc(model.getCtthouseloc());
			p.setCttcarloc(model.getCttcarloc());
			p.setCtthpaytimes(model.getCtthpaytimes());
			p.setCtttaa(model.getCtttaa());
			p.setCalculatecate(model.getCalculatecate());
			String re = pactDao.merge(p);
			setOpres(re);
			return "operateRes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "operateRes";
	}
	/**
	 * modify repay replace man & modify repay date
	 *
	 * @return 2017年7月1日 by 周喜平
	 */
	public String domodify_r_pact() {
		setOpres("250");
		model.setAddtime(new Timestamp(System.currentTimeMillis()));
		Object CR1 = ServletActionContext.getRequest().getParameter("customerreplace1");
		Object CR2 = ServletActionContext.getRequest().getParameter("customerreplace2");

		if (CR1 != null || CR2 != null) {//modify repay replace man
			Customer c = customerDao.findById(model.getCttcustomer());
			if (CR1 != null) {
				c.setCustomerreplace1(CR1.toString());
			}
			if (CR2 != null) {
				c.setCustomerreplace2(CR2.toString());
			}
			customerDao.merge(c);
			setOpres("200");
		}else{//modify repay date
			if (model.getCttdiscount() == null)
				model.setCttdiscount(0);
				Pact pact = pactDao.findById(model.getId());
				pact.setCttloandate(model.getCttloandate());
			String re = pactDao.merge(pact);
			setOpres(re);
		}
		
		return "operateRes";
	}
	/**
	 * add pact into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String addpact() {

		Pact pact = model;
		pact.setId(SessionUtil.getSessionString());
		pact.setAddtime(new Timestamp(System.currentTimeMillis()));
		
		if (pact.getCttdiscount() == null)
			pact.setCttdiscount(0);
		//\cttpaymonth\ctttta\ctttaa\ctthpaytimes\cttspaytimes\cttnowcost\cttnowcosted\cttnowinterest\cttnowinterested\overinterest\overinterested
		//贷款余额在审核通过后，会重新计算，把已经还的本金去掉
		pact.setCttbalance(pact.getCttamount());
		
		pact.setCttpaymonth(0D);
		pact.setCtttta(0);
		if (pact.getCtthpaytimes()!=0){					
			pact.setCttspaytimes(pact.getCtthpaytimes()+1);
		}		
		pact.setCttnowcost(0D);
		pact.setCttnowcosted(0d);
		pact.setCttnowinterest(0d);
		pact.setCttnowinterested(0d);
		pact.setCttspaytimes(0);
		pact.setAuditstate(0);
		pact.setOperator(thissession.getAttribute("_user_id").toString());
		String re = pactDao.save(pact,ctt_serail);
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate
		setOpres(re);
		return "operateRes";
	}

	/**
	 * redirect to pactman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String pactman() {
		return "pactman";
	}
	/**
	 * 提前还款中，打开弹出窗口，显示单个合同的提前还款信息
	 *
	 * @return 2017年10月27日 by 周喜平
	 */
	public String prepayview() {
		Pact pact = pactDao.findById(model.getId());
		if (!pact.getOperator().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!ispactOtherPrivilege()){
			return "noprivilige";
		}
		
		request.put("editpact", pact);
		request.put("leftmoney", pactDao.findLastMoneyById(pact.getId()));
		request.put("ratio", ratioDao.findById(pact.getCttratio()).getRatio());
		return "prepayview";
	}
	/**
	 * redirect to modify loan day and modify the man to repay
	 *
	 * @return 2017年6月28日 by 周喜平
	 */
	public String pactman2() {
		request.put("optype", optype);
		return "pactman2";
	}

	/**
	 * redirect to audit home page
	 *
	 * @return 2017年6月28日 by 周喜平
	 */
	public String pactmanaudit() {
		//setOpres("500");
		//return "operateRes";
		return "pactmanaudit";
	}

	/**
	 * list datatable data on pactman page suggestparam is useful like as suggestPacts,that mean suggestparam parameter is used by ajaxdata4 and suggestPacts
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata4() {

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
		String sqlString = "select t1.*,t3.username from (select PACT.*,CUSTOMER.customername,customer.customerreplace1,customer.customerreplace2 from pact join customer on pact.cttcustomer=customer.id where  cttcode like '%" + suggestparam + "%' OR customername like '%" + suggestparam + "%' OR customerreplace1 like '%" + suggestparam + "%' OR customerreplace2 like '%" + suggestparam + "%') t1  join user t3 on t1.operator=t3.id ";
		
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
		return "ajaxdata4";
	}

	public String ajaxdata5() {

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
		String sqlString = "select t1.*,t3.username from (select PACT.*,CUSTOMER.customername,customer.customerreplace1,customer.customerreplace2 from pact join customer on pact.cttcustomer=customer.id where  cttcode like '%" + suggestparam + "%' OR customername like '%" + suggestparam + "%' OR customerreplace1 like '%" + suggestparam + "%' OR customerreplace2 like '%" + suggestparam + "%') t1  join user t3 on t1.operator=t3.id ";
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
		// System.out.println(sqlString);
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
		
		String sqlString = "select t1.*,t3.username from (select PACT.*,CUSTOMER.customername,customer.customerreplace1,customer.customerreplace2 from pact join customer on pact.cttcustomer=customer.id where  cttcode like '%" + suggestparam + "%' OR customername like '%" + suggestparam + "%' OR customerreplace1 like '%" + suggestparam + "%' OR customerreplace2 like '%" + suggestparam + "%') t1  join user t3 on t1.operator=t3.id ";
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

	/**
	 * list suggest pacts information
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String suggestPacts() {

		List<LinkedHashMap<String, Object>> searchRs = pactDao.getPactInfo(suggestparam);
		request.put("sugestpacts", searchRs);
		return "suggestPacts";
	}
	/**
	 * suggest customer's instalment and arrears
	 *
	 * @return 2017年7月14日 by 周喜平
	 */
	public String suggestPact2() {

		List<LinkedHashMap<String, Object>> searchRs = pactDao.getPactInfo2(suggestparam);
		request.put("sugestpacts", searchRs);
		return "suggestPact2";
	}

	/**
	 * view customer page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String view() {

		Pact pact = pactDao.findById(model.getId());
		if (!pact.getOperator().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!ispactOtherPrivilege()){
			return "noprivilige";
		}
		request.put("editpact", pact);
		request.put("ratio", ratioDao.findById(pact.getCttratio()).getRatio());
		/*
		 * request.put("ccert", cert[checkIntObj(u.getCustomercert())]);
		 * 
		 * request.put("ceduc", educ[checkIntObj(u.getCustomereduc())]);
		 * 
		 * request.put("cgender", gender[checkIntObj(u.getCustomergender())]);
		 * 
		 * request.put("cwed", wed[checkIntObj(u.getCustomerwed())]); System.out.println(wed[checkIntObj(u.getCustomerwed())]);
		 * 
		 * request.put("cresid", resid[checkIntObj(u.getCustomerresid())]);
		 * 
		 * request.put("ccar", car[checkIntObj(u.getCustomercar())]);
		 * 
		 * request.put("cpost", post[checkIntObj(u.getCustomerpost())]);
		 * 
		 * request.put("crealtion1", reali1[checkIntObj(u.getCustomerrealtion1())]);
		 * 
		 * request.put("crealition2", reali2[checkIntObj(u.getCustomerrealition2())]);
		 */
		return "view";
	}
	/**
	 * redirect to view repay plan page
	 *
	 * @return 2017年7月1日 by 周喜平
	 */
	public String viewplan() {
		
		request.put("editpact", model);
		return "viewplan";
	}
	/**
	 * view repay plan table
	 *
	 * @return 2017年7月1日 by 周喜平
	 */
	public String ajaxdata7() {
		
		String sqlString = "select * from repayplan where pid='"+model.getId()+"'  order by paytimes";		
		//System.out.println(sqlString);
		paginationBean.setPageSize(120);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);

		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());		
		suggestparam = "";// delete cache search text last time
		request.put("lrList", searchRs);
		
		return "ajaxdata7";
	}
}
