package com.ioes.lims.action;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;




import com.ioes.lims.beans.PaginationBean;
import com.ioes.lims.daoimp.DeptDAO;
import com.ioes.lims.daoimp.UserDAO;
import com.ioes.lims.idao.ICustomerDAO;
import com.ioes.lims.idao.IDeptDAO;
import com.ioes.lims.idao.IFavoriteDAO;
import com.ioes.lims.idao.IFunDAO;
import com.ioes.lims.idao.IOverdueDAO;
import com.ioes.lims.idao.IPactDAO;
import com.ioes.lims.idao.IProjectDAO;
import com.ioes.lims.idao.IRatioDAO;
import com.ioes.lims.idao.IRepayplanDAO;
import com.ioes.lims.idao.IRepayrecDAO;
import com.ioes.lims.idao.IRoleDAO;
import com.ioes.lims.idao.IRolefunDAO;
import com.ioes.lims.idao.ISysparamDAO;
import com.ioes.lims.idao.IUserDAO;
import com.ioes.lims.idao.IUserroleDAO;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;



public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,RequestAware, SessionAware, ApplicationAware {
	/** 序列化ID */
	private static final long serialVersionUID = -3447248666260840079L;

	/** request作用域 */
	protected Map<String, Object> request;

	/** session作用域 */
	protected Map<String, Object> session;

	/** application作用域 */
	protected Map<String, Object> application;
	protected int iDisplayLength;// for datatable pagination ,pagesize
	protected int iDisplayStart; // for datatable pagination ,page index start
	protected int sEcho; // for datatable pagination
	
	/**
	 * paginate bean
	 */
	@Autowired
	public PaginationBean paginationBean;

	protected T model;
	@Autowired	   
	//@Qualifier("UserDAO")
	IUserDAO userDao;
	@Autowired
	//@Qualifier("DeptDAO")
	IDeptDAO deptDao;
	@Autowired	
	IRoleDAO roleDao;
	@Autowired	
	IFunDAO funDao;
	@Autowired	
	ICustomerDAO customerDao;
	@Autowired	
	IProjectDAO projectDao;
	@Autowired
	ISysparamDAO sysparamDao;
	@Autowired
	IRatioDAO ratioDao;
	@Autowired
	IPactDAO pactDao;
	@Autowired
	IRepayplanDAO repayplanDao;
	@Autowired
	IRepayrecDAO repayrecDao;
	@Autowired
	IOverdueDAO overdueDAO;
	@Autowired
	IRolefunDAO rolefunDAO;
	@Autowired
	IUserroleDAO userroleDAO;
	@Autowired
	IFavoriteDAO favoriteDAO;
	protected HttpSession thissession;
	/**
	 * @return the iDisplayLength
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * @param iDisplayLength the iDisplayLength to set
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
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * @return the sEcho
	 */
	public int getsEcho() {
		return sEcho;
	}

	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public T getModel() {
		return model;
	}
	/**
	 * 构造函数。
	 * 获取泛型的实际类型
	 */
	@SuppressWarnings("unchecked")
	public BaseAction() {
		/**
		 * use lazy-init in applicationContext.xml,
		 * otherwise this constructor will init, error happen at web load
		 * made by kevin
		 */
		ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) paramType.getActualTypeArguments()[0];
		try {
			model = clazz.newInstance();
			thissession=ServletActionContext.getRequest().getSession();
		} catch (Exception e) {
			throw new ActionException(e);
		}
	}
	/**
	 *  get parameter in request,the param like "val,val,val,val"
	 *  return string array
	 *
	 * @param param
	 * @return 2017年6月14日 by 周喜平
	 */
	public String[] splitIDS(String param){		
		String[] idsStrings = null;		
		if (param != null) {
			idsStrings = param.split(",");
		}
		return idsStrings;
	}
	/**
	 * the function is same as splitIDS,but return value
	 *
	 * @param param
	 * @return 2017年7月9日 by 周喜平
	 */
	public List<String> splitIDS2(String param){		
		List<String> idsStringss = new ArrayList<String>();		
		if (param != null) {
			String[] idsStrings = param.split(",");
			for (String string : idsStrings) {
				idsStringss.add(string);
			}
		}
		return idsStringss;
	}
	/**
	 * check o,if o is null ,return 0,else return o
	 *
	 * @param o
	 * @return 2017年6月18日 by 周喜平
	 */
	public Integer checkIntObj(Object o){
		if (null==o){
			return 0;
		}else {
			return Integer.parseInt(o.toString());
		}
	}
	public boolean isAdmin(){
		return (thissession.getAttribute("_user_account").toString()).equalsIgnoreCase("admin");
	}
	public boolean ispactOtherPrivilege(){
		List<String> privis=(List<String>)thissession.getAttribute("_user_privileges");//get all user's privileges		
		return privis.contains("pactOtherData");
	}
	public boolean iscustomerOtherPrivilege(){
		List<String> privis=(List<String>)thissession.getAttribute("_user_privileges");//get all user's privileges		
		return privis.contains("customerOtherData");
	}
}
