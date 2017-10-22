package com.ioes.lims.beans;

import java.util.Date;

public class Repay {
	String customername;//可能是客户姓名或者备注人或者联系人
	Date repayday;
	Date operday;
	String pactcode;
	String pid;
	double payamount;
	
	/**
	 * @return the customername
	 */
	public String getCustomername() {
		return customername;
	}
	/**
	 * @param customername the customername to set
	 */
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return the payamount
	 */
	public double getPayamount() {
		return payamount;
	}
	/**
	 * @param payamount the payamount to set
	 */
	public void setPayamount(double payamount) {
		this.payamount = payamount;
	}
	/**
	 * @return the repayday
	 */
	public Date getRepayday() {
		return repayday;
	}
	/**
	 * @param repayday the repayday to set
	 */
	public void setRepayday(Date repayday) {
		this.repayday = repayday;
	}
	/**
	 * @return the operday
	 */
	public Date getOperday() {
		return operday;
	}
	/**
	 * @param operday the operday to set
	 */
	public void setOperday(Date operday) {
		this.operday = operday;
	}
	/**
	 * @return the pactcode
	 */
	public String getPactcode() {
		return pactcode;
	}
	/**
	 * @param pactcode the pactcode to set
	 */
	public void setPactcode(String pactcode) {
		this.pactcode = pactcode;
	}
	
}
