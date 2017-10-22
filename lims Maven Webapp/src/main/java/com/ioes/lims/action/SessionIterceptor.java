package com.ioes.lims.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;





import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionIterceptor extends AbstractInterceptor {
	private static Logger logger = Logger.getLogger(SessionIterceptor.class);
	private static final long serialVersionUID = 1L;
	public String opres = "ok";
	String[] actionString;
	String uid;
	String uaccount;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#init()
	 */
	@Override
	public void init() {

		super.init();
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {

		ActionContext ctxActionContext = arg0.getInvocationContext();
		// String userString=(String)ctxActionContext.getSession().get("userid");
		HttpServletRequest request = (HttpServletRequest) ctxActionContext.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);


		HttpSession sess = request.getSession();
		String userString = "sessionid:" + sess.getId();
		String uri=request.getRequestURI();
		
		actionString=uri.split("/");
		if (sess.getAttribute("_user_id")==null){
			return "sessiontimeout";
		}else{
			uid=sess.getAttribute("_user_id").toString();
			uaccount=sess.getAttribute("_user_account").toString();
			
			if (StringUtils.isEmpty(uid)){
				return "sessiontimeout";
			}else{
				if (uaccount.equalsIgnoreCase("admin")) return arg0.invoke(); 
				List<String> privis=(List<String>)sess.getAttribute("_user_privileges");//get all user's privileges
				/*for (String string : privis) {
					System.out.println(string+"***");
				}*/
				if (privis.contains(actionString[actionString.length-1])){//contain the url 
					return arg0.invoke();
				}else if(actionString[actionString.length-1].indexOf("index_")!=-1){//not contain the url,the url is index_index.action,it is home page
									
					//return arg0.invoke();
					return "homenoprivilige";
				}else{
					//System.out.println(actionString[actionString.length-1]);
					return "noprivilige";
					//return arg0.invoke();
				}				
				//return "noprivilige";				
			}
		}
		//return "noprivilige";
		
	}

}
