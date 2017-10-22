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
import com.ioes.lims.beans.Fun;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("funAction")
@Scope("prototype")
public class FunAction extends BaseAction<Fun> {
	public String opres;
	private int iDisplayLength;// for datatable pagination ,pagesize
	private int iDisplayStart; // for datatable pagination ,page index start
	private int sEcho; // for datatable pagination
	public String delids;// ids that will be deleted
	public String selectfuns;
	
	/**
	 * @return the selectfuns
	 */
	public String getSelectfuns() {
		return selectfuns;
	}

	/**
	 * @param selectfuns the selectfuns to set
	 */
	public void setSelectfuns(String selectfuns) {
		this.selectfuns = selectfuns;
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
	 * delete fun,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String delfun() {

		funDao.delBatch(splitIDS(delids));
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}

	/**
	 * redirect to edit fun page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String editfun() {

		Fun u = funDao.findById(model.getId());
		request.put("editfun", u);
		return "editfun";
	}

	/**
	 * edit fun information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditfun() {
		String re = funDao.merge(model);
		
		setOpres(re);
		return "operateRes";
	}

	/**
	 * add fun into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String addfun() {

		Fun fun = model;
		fun.setId(SessionUtil.getSessionString());		
		String re = funDao.save(fun);
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate
		setOpres(re);
		return "operateRes";
	}

	/**
	 * redirect to funman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String funman() {
		return "funman";
	}

	/**
	 * list datatable data on funman page suggestparam is useful like as suggestfuns,that mean suggestparam parameter is used by ajaxdata4 and suggestfuns
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata4() {	
		
		String sqlString="select * from fun order by funcode";
		
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		
		
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);
		//System.out.println(sqlString);
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("funs", searchRs);
		/*for (LinkedHashMap<String, Object> linkedHashMap : searchRs) {
			System.out.println(linkedHashMap.get("FUNCODE"));
		}*/
		return "ajaxdata4";
	}	
	/**
	 * look up function for setting role's function
	 * direct to look up page
	 * @return 2017年7月8日 by 周喜平
	 */
	public String lookfun() {
		return "lookfun";
	}
	/**
	 * look up function for setting role's function
	 *
	 * @return 2017年7月8日 by 周喜平
	 */
	public String lookfuntable() {	
		
		String sqlString="select * from fun order by funcode";
		
		
		List<String> selectfunsList=splitIDS2(selectfuns);
		paginationBean.setPageSize(iDisplayLength);
		
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;
		List<LinkedHashMap<String, Object>> searchRs2=new ArrayList<LinkedHashMap<String,Object>>();
		searchRs = paginationBean.getCurrPageData(sqlString);
		
		for (LinkedHashMap<String, Object> linkedHashMap : searchRs) {
			if (selectfunsList.contains(linkedHashMap.get("ID").toString())){
				linkedHashMap.put("SELECTED", "1");
			}else{
				linkedHashMap.put("SELECTED", "0");
			}
			searchRs2.add(linkedHashMap);
		}
		//System.out.println(searchRs.size());
		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("funs", searchRs2);		
		return "lookfuntable";
	}
}
