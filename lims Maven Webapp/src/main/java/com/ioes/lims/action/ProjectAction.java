package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Project;
import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("projectAction")
@Scope("prototype")
public class ProjectAction extends BaseAction<Project> {
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
	/**
	 * delete project,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String delproject() {

		projectDao.delBatch(splitIDS(delids));
		
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}

	/**
	 * redirect to edit project page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String editproject() {

		Project u = projectDao.findById(model.getId());
		request.put("editproject", u);

		return "editproject";
	}

	/**
	 * edit project information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditproject() {
		model.setAddtime(new Timestamp(System.currentTimeMillis()));
		model.setSerial(projectDao.findById(model.getId()).getSerial());
		String re = projectDao.merge(model);
		
		setOpres(re);
		return "operateRes";
	}

	/**
	 * add project into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String addproject() {

		Project project = model;
		project.setId(SessionUtil.getSessionString());
		project.setAddtime(new Timestamp(System.currentTimeMillis()));
		String re = projectDao.save(project);
		
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate
		setOpres(re);
		return "operateRes";
	}

	/**
	 * redirect to projectman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String projectman() {
		return "projectman";
	}

	/**
	 * list datatable data on projectman page suggestparam is useful like as suggestprojects,that mean suggestparam parameter is used by ajaxdata4 and suggestprojects
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata5() {		
		if (suggestparam == null)
			suggestparam = "";
		String sqlString="select * from project t1 where t1.pname like '%" + suggestparam + "%' ";
		
		//System.out.println(sqlString);
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);
		
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("projects", searchRs);
		suggestparam = "";// delete cache search text last time
		return "ajaxdata5";
	}
	/**
	 * list suggest Project information
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String suggestProjects() {				
		
		List<LinkedHashMap<String, Object>> searchRs = projectDao.getProjectInfo(suggestparam);
		request.put("sugestprojects", searchRs);
		return "suggestProjects";
	}


}
