package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.User;
import com.ioes.lims.utils.IdcardValidator;
import com.sias.util.SessionUtil;

@Service("customerAction")
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {
	public String opres;
	private int iDisplayLength;// for datatable pagination ,pagesize
	private int iDisplayStart; // for datatable pagination ,page index start
	private int sEcho; // for datatable pagination
	public String delids;// ids that will be deleted
	String suggestparam;// suggest parameter
    
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
	 * @return the sEcho
	 */
	public int getsEcho() {
		return sEcho;
	}

	/**
	 * @param sEcho
	 *            the sEcho to set
	 */
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * @return the iDisplayLength
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * @param iDisplayLength
	 *            the iDisplayLength to set
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * @return the iDisplayStart
	 */
	public int getiDisplayStart() {
		return iDisplayStart;
	}

	/**
	 * @param iDisplayStart
	 *            the iDisplayStart to set
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * @param opres
	 *            the opres to set
	 */
	public void setOpres(String opres) {
		this.opres = opres;
	}
	public String execute() {
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}
	/**
	 * delete customer,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String delcustomer() {
		String[] objs=splitIDS(delids);
		Customer u=null;
		if (!isAdmin())			
			for (String string : objs) {
				u=customerDao.findById(string);
				if (u.getUser()==null){
					if (!iscustomerOtherPrivilege()){//can not access other user's customers					
						setOpres("{\"state\":\"310\"}");
						return "operateRes";
					}
					
				}else if (!u.getUser().getId().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!iscustomerOtherPrivilege()){
					setOpres("{\"state\":\"310\"}");
					return "operateRes";
				}
			}
		customerDao.delBatch(splitIDS(delids));
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}

	/**
	 * redirect to edit customer page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String editcustomer() {
		
		Customer u = customerDao.findById(model.getId());
		if (!isAdmin()){
			
			if (u.getUser()==null){
				
				if (!iscustomerOtherPrivilege()){//can not access other user's customers					
					return "noprivilige";
				}				
			}else if (!u.getUser().getId().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!iscustomerOtherPrivilege()){
				return "noprivilige";
			}
		}
		request.put("editcustomer", u);
		return "editcustomer";
	}
	/**
	 * view customer page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String view() {
		String[] cert ={"身份证","其他"};
		String[] educ={"研究生以上","大学本科","大学专科和专科","高中及以下"};
		String[] gender={"男","女"};
		String[] wed={"已婚","未婚","离异","丧偶"};
		String[] resid={"全款","按揭","租用","集体宿舍","亲戚或父母家","其它"};
		String[] car={"无","5-15万元","15-30万元","30万元以上"};
		String[] post={"单位主管（局级及以上）","单位中层（处级及以上）","部门主管（科级及以上）","专业技术人员","下岗或失业","其它"};
		String[] reali1={"父母","配偶","兄弟，姐妹","朋友","其它"};
		String[] reali2=reali1;
		Customer u = customerDao.findById(model.getId());
		
		if (!isAdmin()){		
			if (u.getUser()==null){
				//System.out.println(iscustomerOtherPrivilege());
				if (!iscustomerOtherPrivilege()){//can not access other user's customers					
					return "noprivilige";
				}
				
			}else if (!u.getUser().getId().equalsIgnoreCase(thissession.getAttribute("_user_id").toString())&&!isAdmin()&&!iscustomerOtherPrivilege()){
				return "noprivilige";
			}
		}
		request.put("editcustomer", u);		
		request.put("ccert", cert[checkIntObj(u.getCustomercert())]);		
		request.put("ceduc", educ[checkIntObj(u.getCustomereduc())]);		
		request.put("cgender", gender[checkIntObj(u.getCustomergender())]);		
		request.put("cwed", wed[checkIntObj(u.getCustomerwed())]);
		//System.out.println(wed[checkIntObj(u.getCustomerwed())]);		
		request.put("cresid", resid[checkIntObj(u.getCustomerresid())]);		
		request.put("ccar", car[checkIntObj(u.getCustomercar())]);		
		request.put("cpost", post[checkIntObj(u.getCustomerpost())]);		
		
		request.put("ctcert", cert[checkIntObj(u.getCustomertcert())]);		
		request.put("cteduc", educ[checkIntObj(u.getCustomerteduc())]);		
		request.put("ctgender", gender[checkIntObj(u.getCustomertgender())]);		
		request.put("ctwed", wed[checkIntObj(u.getCustomertwed())]);
		
		request.put("ctresid", resid[checkIntObj(u.getCustomertresid())]);		
		request.put("ctcar", car[checkIntObj(u.getCustomertcar())]);		
		request.put("ctpost", post[checkIntObj(u.getCustomertpost())]);
		
		request.put("crealtion1", reali1[checkIntObj(u.getCustomerrealtion1())]);		
		request.put("crealition2", reali2[checkIntObj(u.getCustomerrealition2())]);
		
		return "view";
	}

	/**
	 * edit customer information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditcustomer() {
		model.setAddtime(new Timestamp(System.currentTimeMillis()));
		String re = customerDao.merge(model);
		setOpres(re);
		return "operateRes";
	}

	/**
	 * add customer into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String addcustomer() {
		
		Customer customer = model;
		customer.setId(SessionUtil.getSessionString());
		customer.setAddtime(new Timestamp(System.currentTimeMillis()));
		User user=userDao.findById(thissession.getAttribute("_user_id").toString());
		customer.setUser(user);
		
		//validate identify card
		if (customer.getCustomercert()==0){
		
			
			if (!IdcardValidator.isValidatedAllIdcard(customer.getCustomercertnum())) {
				setOpres("400");
				return "operateRes";
			}
		}
		
		String re = customerDao.save(customer);
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate,400 is identify is invalid
		setOpres(re);
		return "operateRes";
		
	}

	/**
	 * redirect to customerman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String customerman() {
		return "customerman";
	}

	/**
	 * list datatable data on customerman page suggestparam is useful like as suggestcustomers,that mean suggestparam parameter is used by ajaxdata4 and suggestcustomers
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata4() {

		/*Enumeration<String> aEnumeration = ServletActionContext.getRequest().getParameterNames();
		while (aEnumeration.hasMoreElements()) {
			System.out.println(aEnumeration.nextElement()+":"+ServletActionContext.getRequest().getParameter(aEnumeration.nextElement()).toString());
		}*/
		String orderColIndex=null;
		String orderMethod=null;
		Object orderColIndex0=ServletActionContext.getRequest().getParameter("iSortCol_0");
		Object orderMethod0=ServletActionContext.getRequest().getParameter("sSortDir_0");
		if (orderColIndex0!=null){
			orderColIndex=orderColIndex0.toString();
			orderMethod=orderMethod0.toString();
		}
			
		if (!StringUtils.isNotEmpty(orderColIndex)){
			orderColIndex="-1";
			orderMethod="";
		}
		String order_0=" order by t1.addtime desc";
		String order_1=" order by t1.customername ";
		String order_2=" order by t1.customercertnum ";
		if (suggestparam == null)
			suggestparam = "";
		String sqlString="select t1.* from customer t1  where t1.customername like '%" + suggestparam + "%' or t1.customercertnum like '%" + suggestparam + "%' ";
		if (orderColIndex.equals("1")) {
			order_1+=orderMethod;
			sqlString+=order_1;
		}else if (orderColIndex.equals("2")) {
			order_2+=orderMethod;
			sqlString+=order_2;
		}else{
			sqlString+=order_0;
		}
		//System.out.println(sqlString);
		if (iDisplayLength==0) iDisplayLength=10;
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		//System.out.println(iDisplayLength+":"+iDisplayStart);	
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);

		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("customers", searchRs);
		suggestparam = "";// delete cache search text last time
		return "ajaxdata4";
	}

	/**
	 * list suggest customers information
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String suggestCustomers() {

		List<LinkedHashMap<String, Object>> searchRs = customerDao.getCustomerInfo(suggestparam);
		request.put("sugestcustomers", searchRs);
		return "suggestCustomers";
	}

}
