package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AbstractRepayplan entity provides the base persistence definition of the Repayplan entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRepayplan implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private Integer paytimes;
	private Double payamount;
	private Timestamp addtime;
	private Double cost;
	private Double interest;
	private Date payday;

	// Constructors

	/** default constructor */
	public AbstractRepayplan() {
	}

	/** minimal constructor */
	public AbstractRepayplan(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractRepayplan(String id, String pid, Integer paytimes, Double payamount, Timestamp addtime, Double cost, Double interest, Date payday) {
		this.id = id;
		this.pid = pid;
		this.paytimes = paytimes;
		this.payamount = payamount;
		this.addtime = addtime;
		this.cost = cost;
		this.interest = interest;
		this.payday = payday;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getPaytimes() {
		return this.paytimes;
	}

	public void setPaytimes(Integer paytimes) {
		this.paytimes = paytimes;
	}

	public Double getPayamount() {
		return this.payamount;
	}

	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getInterest() {
		return this.interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Date getPayday() {
		return this.payday;
	}

	public void setPayday(Date payday) {
		this.payday = payday;
	}

}