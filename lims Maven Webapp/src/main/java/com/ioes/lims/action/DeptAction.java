package com.ioes.lims.action;

import java.util.Enumeration;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("deptAction")
@Scope("prototype")
public class DeptAction extends BaseAction<Dept> {
	public String opres;// operator result
	public String delids;//ids that will be deleted
	
	/**
	 * @return the delids
	 */
	public String getDelids() {
		return delids;
	}
	/**
	 * @param delids the delids to set
	 */
	public void setDelids(String delids) {
		this.delids = delids;
	}
	/**
	 * 空参构造
	 */
	public DeptAction() {}
	/**
	 * @param opres the opres to set
	 */
	public void setOpres(String opres) {
		this.opres = opres;
	}
	public String execute(){
		return "test";
	}
	public String deptman(){
		List<Dept> listDept=(List<Dept>)deptDao.findAll();
		request.put("depts", listDept);
		return "deptman";
	}
	/**
	 * display dialog for select department
	 *
	 * @return 2017年6月12日 by 周喜平
	 */
	public String depttree(){
		List<Dept> listDept=(List<Dept>)deptDao.findAll();
		request.put("depts", listDept);
		return "depttree";
	}
	
	/**
	 * del dept
	 *
	 * @return 2017年6月12日 by 周喜平
	 */
	public String deldept(){
		
		deptDao.delBatch(splitIDS(delids));
		
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}
	/**
	 * edit department
	 *
	 * @return 2017年6月12日 by 周喜平
	 */
	public String editdept(){		
		Dept dept=model;
		if (!model.getId().equals("0")){
			deptDao.merge(dept);
		}		
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}
	
	/**
	 * add dept into db
	 *
	 * @return 2017年6月12日 by 周喜平
	 */
	public String adddept(){
		
		/*Enumeration<String> aEnumeration=ServletActionContext.getRequest().getParameterNames();
		while(aEnumeration.hasMoreElements()){
			System.out.println(aEnumeration.nextElement());
		}
		System.out.println("))))))))))))"+ServletActionContext.getRequest().getParameter("account"));
		System.out.println("))))))))))))"+model.getAccount());*/
		Dept dept=model;
		
		String newid=SessionUtil.getSessionString();
		dept.setId(newid);
		deptDao.save(dept);
		
		setOpres("{\"state\":\"200\",\"newid\":\""+newid+"\"}");
		return "operateRes";
	}
}
