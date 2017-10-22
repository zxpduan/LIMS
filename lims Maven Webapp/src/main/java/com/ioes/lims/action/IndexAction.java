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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;





import com.ioes.lims.beans.Favorite;
import com.ioes.lims.beans.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sias.util.Initbean;
import com.sias.util.RedisUitl;
import com.sias.util.SessionUtil;
import com.sias.util.dataTypeUtil;
@Service("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction<User> {
	private static Logger logger = Logger.getLogger(IndexAction.class);
		
	public String index(){
		HttpServletRequest request1=ServletActionContext.getRequest();
		String userid=request1.getSession().getAttribute("_user_id").toString();//user id
		User u=userDao.findById(userid);
		request.put("username", u.getUsername());
		
		List <Favorite> lfs=favoriteDAO.findByUserid(thissession.getAttribute("_user_id").toString());
		for (Favorite favorite : lfs) {
		
			switch (favorite.getFavcode()) {
			case 1:
		
				request.put("navfix", favorite.getFavvalue());
				break;
			case 2:
		
				request.put("leftnavfix", favorite.getFavvalue());
				break;
			case 3:
		
				request.put("skin", favorite.getFavvalue());
				break;
			case 4:
				request.put("deskfun", favorite.getFavvalue());
				break;	
			case 5:
				request.put("breakcrumbsfix", favorite.getFavvalue());
				break;
			}
			
		}
		
		return "index";
	}

}
