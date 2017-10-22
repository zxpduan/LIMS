package com.ioes.lims.action;

import java.awt.Dialog.ModalExclusionType;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.enterprise.inject.New;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Role;
import com.ioes.lims.beans.User;
import com.ioes.lims.beans.Userrole;
import com.sias.util.SessionUtil;

@Service("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	public String opres;
	private int iDisplayLength;// for datatable pagination ,pagesize
	private int iDisplayStart; // for datatable pagination ,page index start
	private int sEcho; // for datatable pagination
	public String delids;// ids that will be deleted
	String suggestparam;// suggest parameter
    public Userrole userrole;
    String oldpw;
    
	

	/**
	 * @return the oldpw
	 */
	public String getOldpw() {
		return oldpw;
	}

	/**
	 * @param oldpw the oldpw to set
	 */
	public void setOldpw(String oldpw) {
		this.oldpw = oldpw;
	}

	/**
	 * @return the userrole
	 */
	public Userrole getUserrole() {
		return userrole;
	}

	/**
	 * @param userrole the userrole to set
	 */
	public void setUserrole(Userrole userrole) {
		this.userrole = userrole;
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

	public String execute() {
		//User user = model;
		//User user1 = new User(SessionUtil.getSessionString(), "aa", "澳洲", "124", "dept");
		//userDao.save(user1);
		return "test";
	}

	/**
	 * delete user,enable delete batch
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String deluser() {

		userDao.delBatch(splitIDS(delids));
		setOpres("{\"state\":\"200\"}");
		return "operateRes";
	}

	/**
	 * redirect to edit user page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String edituser() {

		User u = userDao.findById(model.getId());

		Dept dept = deptDao.findById(u.getDept());
		List<Userrole> lu=userroleDAO.findByUserid(u.getId());		
		if (lu!=null&&lu.size()>0){			
			request.put("role", roleDao.findById(lu.get(0).getRoleid()));
		}else{
			Role role=new Role();
			role.setId(SessionUtil.getSessionString());
			role.setRolename("");
			request.put("role", role);
		}
		request.put("edituser", u);
		request.put("edituser_dept", dept);

		return "edituser";
	}

	/**
	 * edit user information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doedituser() {
		model.setAddtime(new Timestamp(System.currentTimeMillis()));
		userrole.setId(SessionUtil.getSessionString());
		userroleDAO.save(userrole);		
		
		String re = userDao.merge(model);
		setOpres(re);
		return "operateRes";
	}

	/**
	 * add user into db
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String adduser() {

		User user = model;
		user.setId(SessionUtil.getSessionString());
		user.setAddtime(new Timestamp(System.currentTimeMillis()));
		if (user.getPw().isEmpty())
			user.setPw("123456");
		userrole.setId(SessionUtil.getSessionString());
		userrole.setUserid(user.getId());
		userroleDAO.save(userrole);		
		String re = userDao.save(user);
		// the value of re is 200,300,250.200 is success,250 is db error,300 is dublicate
		setOpres(re);
		return "operateRes";
	}

	/**
	 * redirect to userman
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String userman() {
		return "userman";
	}

	/**
	 * list datatable data on userman page suggestparam is useful like as suggestUsers,that mean suggestparam parameter is used by ajaxdata4 and suggestUsers
	 * 
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ajaxdata4() {

		/*Enumeration<String> aEnumeration = ServletActionContext.getRequest().getParameterNames();
		while (aEnumeration.hasMoreElements()) {
			System.out.println(aEnumeration.nextElement()+":"+ServletActionContext.getRequest().getParameter(aEnumeration.nextElement()).toString());
		}*/
		String orderColIndex=ServletActionContext.getRequest().getParameter("iSortCol_0").toString();
		String orderMethod=ServletActionContext.getRequest().getParameter("sSortDir_0").toString();
		//System.out.println(orderColIndex+":"+orderMethod);
		String order_0=" order by t1.addtime desc";
		String order_1=" order by t1.account ";
		String order_2=" order by t1.username ";
		if (suggestparam == null)
			suggestparam = "";
		String sqlString="select t1.*,t2.name,t4.rolename from user t1 join dept t2 on t1.dept=t2.id join userrole t3 on t1.id=t3.userid join role t4 on t4.id=t3.roleid where t1.account like '%" + suggestparam + "%' or t1.username like '%" + suggestparam + "%' ";
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
		paginationBean.setPageSize(iDisplayLength);
		iDisplayStart=iDisplayStart/iDisplayLength+1;
		paginationBean.setCurrPage(iDisplayStart);
		List<LinkedHashMap<String, Object>> searchRs;

		searchRs = paginationBean.getCurrPageData(sqlString);

		request.put("sEcho", sEcho);
		request.put("iTotalRecords", paginationBean.getRowCount());
		request.put("iTotalDisplayRecords", paginationBean.getRowCount());
		request.put("users", searchRs);
		suggestparam = "";// delete cache search text last time
		return "ajaxdata4";
	}

	/**
	 * list suggest users information
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String suggestUsers() {
		
		List<LinkedHashMap<String, Object>> searchRs = userDao.getUserInfo(suggestparam);
		request.put("sugestusers", searchRs);
		return "suggestUsers";
	}
	/**
	 * 个人信息
	 *
	 * @return 2017年7月9日 by 周喜平
	 */
	public String userinfo() {
		//System.out.println(thissession.getAttribute("_user_id").toString());
		User userinfo = userDao.findById(thissession.getAttribute("_user_id").toString());
		if (userinfo==null){
			request.put("userinfo", new User());
			request.put("deptinfo", null);
			request.put("rolename", null);
			return "userinfo";
		}
		//System.out.println(userinfo.getId());
		Dept deptinfo=new Dept();
		if (userinfo.getDept()!=null&&userinfo.getDept()!=""){
			deptinfo=deptDao.findById(userinfo.getDept());
		}else{
			deptinfo.setName("超级用户");
		}		
		List<Userrole> ur=userroleDAO.findByUserid(userinfo.getId());
		String rolename="";
		for (Userrole userrole : ur) {
			Role r=roleDao.findById(userrole.getRoleid());
			rolename+=" "+r.getRolename();
		}
		request.put("userinfo", userinfo);
		request.put("deptinfo", deptinfo.getName());
		request.put("rolename", rolename);
		return "userinfo";
	}
	/**
	 * modify password
	 *
	 * @return 2017年7月9日 by 周喜平
	 */
	public String chpw(){
		
		User user = userDao.findById(thissession.getAttribute("_user_id").toString());
		
		if (!oldpw.equalsIgnoreCase(user.getPw())) {
			setOpres("250");
			return "operateRes";
		}
		user.setPw(model.getPw());		
		setOpres(userDao.merge(user));
		return "operateRes";
	}
}
