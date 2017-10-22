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
import com.ioes.lims.beans.Favorite;
import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.Sysparam;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("sysAction")
@Scope("prototype")
public class SysAction extends BaseAction<Sysparam> {
	public String opres;
	public String navfix;
	public String leftnavfix;
	public String skin;
	public String deskfun;
	public String breakcrumbsfix;
	
	
	/**
	 * @return the breakcrumbsfix
	 */
	public String getBreakcrumbsfix() {
		return breakcrumbsfix;
	}

	/**
	 * @param breakcrumbsfix the breakcrumbsfix to set
	 */
	public void setBreakcrumbsfix(String breakcrumbsfix) {
		this.breakcrumbsfix = breakcrumbsfix;
	}

	/**
	 * @return the navfix
	 */
	public String getNavfix() {
		return navfix;
	}

	/**
	 * @param navfix the navfix to set
	 */
	public void setNavfix(String navfix) {
		this.navfix = navfix;
	}

	/**
	 * @return the leftnavfix
	 */
	public String getLeftnavfix() {
		return leftnavfix;
	}

	/**
	 * @param leftnavfix the leftnavfix to set
	 */
	public void setLeftnavfix(String leftnavfix) {
		this.leftnavfix = leftnavfix;
	}

	/**
	 * @return the skin
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * @param skin the skin to set
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * @return the deskfun
	 */
	public String getDeskfun() {
		return deskfun;
	}

	/**
	 * @param deskfun the deskfun to set
	 */
	public void setDeskfun(String deskfun) {
		this.deskfun = deskfun;
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
	 * redirect to add page
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String addRatio() {
		
		return "addRatio";
	}

	/**
	 * edit sysparam information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditsysparam() {
		
		String re = sysparamDao.merge(model);
		setOpres(re);
		return "operateRes";
	}

	

	/**
	 * redirect to ratio,this method include ratio and over day setting.
	 *
	 * @return 2017年6月13日 by 周喜平
	 */
	public String ratio() {
		List<Ratio> rs = ratioDao.findAll();
		request.put("editratios", rs);
		Sysparam u = sysparamDao.findOnlyOne();
		request.put("editsysparam", u);
		return "ratio";
	}
	/**
	 * redirect to preference
	 *
	 * @return 2017年7月9日 by 周喜平
	 */
	public String preference() {
		
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
		return "preference";
	}
	/**
	 * 保存偏好	
	 *
	 * @return 2017年7月9日 by 周喜平
	 */
	public String savePreference() {
		try {
			
			String uid=thissession.getAttribute("_user_id").toString();
			List<Favorite> lv=favoriteDAO.findByUserid(uid);
			for (Favorite favorite1 : lv) {
				favoriteDAO.delete(favorite1);
			}
			if (!StringUtils.isEmpty(navfix)){
				Favorite favorite=new Favorite();
				favorite.setId(SessionUtil.getSessionString());
				favorite.setFavcode(1);
				favorite.setUserid(uid);
				favorite.setFavvalue(navfix);
				/*for (Favorite favorite1 : lv) {
					if (favorite1.getFavcode()==1){
						favoriteDAO.delete(favorite1);
					}			
					
				}*/
				favoriteDAO.save(favorite);
			}
			if (!StringUtils.isEmpty(leftnavfix)){
				Favorite favorite=new Favorite();
				favorite.setId(SessionUtil.getSessionString());
				favorite.setFavcode(2);
				favorite.setUserid(uid);
				favorite.setFavvalue(leftnavfix);	
				/*for (Favorite favorite1 : lv) {
					if (favorite1.getFavcode()==2){
						favoriteDAO.delete(favorite1);
					}			
					
				}*/
				favoriteDAO.save(favorite);
			}
			if (!StringUtils.isEmpty(skin)){
				Favorite favorite=new Favorite();
				favorite.setId(SessionUtil.getSessionString());
				favorite.setFavcode(3);
				favorite.setUserid(uid);
				favorite.setFavvalue(skin);		
				/*for (Favorite favorite1 : lv) {
					if (favorite1.getFavcode()==3){
						favoriteDAO.delete(favorite1);
					}			
					
				}*/
				favoriteDAO.save(favorite);
			}
			if (!StringUtils.isEmpty(deskfun)){
				Favorite favorite=new Favorite();
				favorite.setId(SessionUtil.getSessionString());
				favorite.setFavcode(4);
				favorite.setUserid(uid);
				favorite.setFavvalue(deskfun);	
				/*for (Favorite favorite1 : lv) {
					if (favorite1.getFavcode()==5){
						favoriteDAO.delete(favorite1);
					}			
					
				}*/
				favoriteDAO.save(favorite);
			}
			if (!StringUtils.isEmpty(breakcrumbsfix)){
				Favorite favorite=new Favorite();
				favorite.setId(SessionUtil.getSessionString());
				favorite.setFavcode(5);
				favorite.setUserid(uid);
				favorite.setFavvalue(breakcrumbsfix);	
				/*for (Favorite favorite1 : lv) {
					if (favorite1.getFavcode()==4){
						favoriteDAO.delete(favorite1);
					}			
					
				}*/
				favoriteDAO.save(favorite);
			}
			setOpres("200");
			return "operateRes";
		} catch (Exception e) {
			e.printStackTrace();
			setOpres("250");
			return "operateRes";
		}
		
	}
	

}
