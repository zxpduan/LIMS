package com.ioes.lims.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.PSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;








import com.ioes.lims.beans.Fun;
import com.ioes.lims.beans.Rolefun;
import com.ioes.lims.beans.User;
import com.ioes.lims.beans.Userrole;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sias.util.Initbean;
import com.sias.util.RedisUitl;
import com.sias.util.SessionUtil;
import com.sias.util.dataTypeUtil;
@Service("loginAction")
@Scope("prototype")
public class loginAction extends BaseAction<User> {
	private static Logger logger = Logger.getLogger(loginAction.class);
	
	String msg="";
	String loginuri;//用户登录的uri
	/**
	 * @return the loginuri
	 */
	public String getLoginuri() {
		return loginuri;
	}
	/**
	 * @param loginuri the loginuri to set
	 */
	public void setLoginuri(String loginuri) {
		this.loginuri = loginuri;
	}
	
	
	public String getMsg() {
		return msg;
	}
	public String exit() {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.getSession().setAttribute("_user_name", "");
		request.getSession().setAttribute("_user_id", "");
		request.getSession().setAttribute("_user_privileges", "");
		//request.getSession().setAttribute("_user_role", userMap.get("id").toString());					
		request.getSession().setAttribute("_user_account", "");
		if (request.getSession().getAttribute("login_uri")!=null){
			loginuri=request.getSession().getAttribute("login_uri").toString();
		}		
		return "sessiontimeout";
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void doajaxrequest(){
		loginuri="";
		HttpServletResponse response=ServletActionContext.getResponse();
		//total
		HttpServletRequest request=ServletActionContext.getRequest();
		//System.out.println(request.getRequestURL().toString());		
		//System.out.println(request.getHeader("referer").toString());		
		String sourcePathString=request.getHeader("referer").toString();		
		String sps[]=sourcePathString.split("/");
		for (int i=3;i<sps.length;i++) {
			loginuri+="/"+sps[i];
			//System.out.println(i+":"+sps[i]);
		}		
		String username=request.getParameter("username");
		String pwString=request.getParameter("password");
		String verifycode=request.getParameter("verifycode");
		String vcode=request.getSession().getAttribute("validateCode").toString();		
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out=response.getWriter();
			String jsonString="";
			if (!verifycode.equalsIgnoreCase(vcode)) {
				jsonString="{\"username\":\""+username+"\",\"isok\":\"4\",\"pw\":\""+pwString+"\"}";//验证码错
				out.write(jsonString);
				out.flush();
				out.close();
				return;
			}						
			User user=userDao.findByAccountPw(username, pwString);				
			if(user!=null){
				jsonString="{\"username\":\""+username+"\",\"isok\":\"1\",\"pw\":\""+pwString+"\"}";//ok
				request.getSession().setAttribute("_user_name", user.getUsername());//user id									
				request.getSession().setAttribute("_user_account", username);//user account
				request.getSession().setAttribute("_user_id", user.getId());//user account
				List<Userrole> userroles=userroleDAO.findByUserid(user.getId());
				List<String> privileges=new ArrayList<String>();
				Fun f=null;
				for (Userrole userrole : userroles) {
					List<Rolefun> rfs=rolefunDAO.findByRoleid(userrole.getRoleid());
					for (Rolefun rolefun : rfs) {
						f=funDao.findById(rolefun.getFunid());
						if (f!=null){
							privileges.add(f.getFunrul());
							logger.info(f.getFunrul()+"_____________________");
						}
						
						
					}
				}
				request.getSession().setAttribute("_user_privileges", privileges);//user privileges
			}else{//用户名不存在或密码错误
					jsonString="{\"username\":\""+username+"\",\"isok\":\"3\",\"pw\":\""+pwString+"\"}";
			}
			//System.out.println(jsonString);
			out.write(jsonString);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
