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

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.Dept;
import com.ioes.lims.beans.Ratio;
import com.ioes.lims.beans.Sysparam;
import com.ioes.lims.beans.User;
import com.sias.util.SessionUtil;

@Service("ratioAction")
@Scope("prototype")
public class RatioAction extends BaseAction<Ratio> {
	public String opres;

	/**
	 * @param opres
	 *            the opres to set
	 */
	public void setOpres(String opres) {
		this.opres = opres;
	}

	public String execute() {		
		return "test";
	}

	/**
	 * redirect to add ratio page
	 *
	 * @return 2017年6月22日 by 周喜平
	 */
	public String addRatio() {

		return "addRatio";
	}

	/**
	 * save to db
	 *
	 * @return 2017年6月22日 by 周喜平
	 */
	public String saveRatio() {
		String idString = SessionUtil.getSessionString();
		model.setId(idString);
		String re = ratioDao.save(model);
		setOpres("{\"state\":\"" + re + "\",\"id\":\"" + idString + "\"}");
		return "operateRes";
	}

	public String delRatio() {

		ratioDao.delete(model);
		setOpres("200");
		return "operateRes";
	}

	/**
	 * edit ratio information into db
	 *
	 * @return 2017年6月15日 by 周喜平
	 */
	public String doeditratio() {

		String re = ratioDao.merge(model);
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
		return "ratio";
	}

}
