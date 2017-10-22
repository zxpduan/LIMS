package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.Rolefun;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	public String opres;
	private int iDisplayLength;// for datatable pagination ,pagesize
	private int iDisplayStart; // for datatable pagination ,page index start
	private int sEcho; // for datatable pagination
	public String delids;// ids that will be deleted
	String suggestparam;// suggest parameter
	String funs;//role's functions
	
	/**
	 * @return the funs
	 */
	public String getFuns() {
		return funs;
	}

	/**
	 * @param funs the funs to set
	 */
	public void setFuns(String funs) {
		this.funs = funs;
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
	/**
	 * delete role,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String delrole() {

		roleDao.delBatch(splitIDS(delids));
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}

	/**
	 * redirect to edit role page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String editrole() {

		Role u = roleDao.findById(model.getId());		
		request.put("editrole", u);
		List<Rolefun> rolefuns=rolefunDAO.findByProperty("roleid", u.getId());
		request.put("rolefuns", rolefuns);	
		
		return "editrole";
	}

	/**
	 * edit role information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditrole() {
		String re = roleDao.merge(model);
		rolefunDAO.saveRoleFun(splitIDS(funs),model.getId());
		setOpres(re);
		return "operateRes";
	}

	/**
	 * add role into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String addrole() {

		Role role = model;
		role.setId(SessionUtil.getSessionString());
		String re = roleDao.save(role);
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate
		setOpres(re);
		return "operateRes";
	}

	/**
	 * redirect to roleman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String roleman() {
		return "roleman";
	}

	/**
	 * list datatable data on roleman page suggestparam is useful like as suggestroles,that mean suggestparam parameter is used by ajaxdata4 and suggestroles
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata5() {		
		if (suggestparam == null)
			suggestparam = "";
		String sqlString="select * from role t1 where t1.rolename like '%" + suggestparam + "%' ";
		
		//System.out.println(sqlString);
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("roles", searchRs);
		suggestparam = "";// delete cache search text last time
		return "ajaxdata5";
	}	
	/**
	 * look up role for setting user's role
	 * direct to look up page
	 * @return 2017年7月8日 by 周喜平
	 */
	public String lookrole() {		
		return "lookrole";
	}
	/**
	 * look up role for setting user's role
	 *
	 * @return 2017年7月8日 by 周喜平
	 */
	public String lookroletable() {		
		if (suggestparam == null)
			suggestparam = "";
		String sqlString="select * from role t1 where t1.rolename like '%" + suggestparam + "%' ";
		
		//System.out.println(sqlString);
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("roles", searchRs);
		suggestparam = "";// delete cache search text last time
		return "lookroletable";
	}
}
