package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AbstractOverdue entity provides the base persistence definition of the Overdue entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOverdue implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private String planid;
	private Double amount;
	private Date startdate;
	private Double overinterest;
	private Timestamp addtime;
	private Integer overday;
	private Double payoff;
	private Double repayinterest;
	private Date updatedate;

	// Constructors

	/** default constructor */
	public AbstractOverdue() {
	}

	/** minimal constructor */
	public AbstractOverdue(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractOverdue(String id, String pid, String planid, Double amount, Date startdate, Double overinterest, Timestamp addtime, Integer overday, Double payoff, Double repayinterest, Date updatedate) {
		this.id = id;
		this.pid = pid;
		this.planid = planid;
		this.amount = amount;
		this.startdate = startdate;
		this.overinterest = overinterest;
		this.addtime = addtime;
		this.overday = overday;
		this.payoff = payoff;
		this.repayinterest = repayinterest;
		this.updatedate = updatedate;
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

	public String getPlanid() {
		return this.planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Double getOverinterest() {
		return this.overinterest;
	}

	public void setOverinterest(Double overinterest) {
		this.overinterest = overinterest;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Integer getOverday() {
		return this.overday;
	}

	public void setOverday(Integer overday) {
		this.overday = overday;
	}

	public Double getPayoff() {
		return this.payoff;
	}

	public void setPayoff(Double payoff) {
		this.payoff = payoff;
	}

	public Double getRepayinterest() {
		return this.repayinterest;
	}

	public void setRepayinterest(Double repayinterest) {
		this.repayinterest = repayinterest;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

}