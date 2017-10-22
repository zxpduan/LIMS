package com.ioes.lims.beans;

import java.sql.Timestamp;

/**
 * AbstractPayarrearsrec entity provides the base persistence definition of the Payarrearsrec entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPayarrearsrec implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private String planid;
	private Timestamp addtime;
	private Double repayamount;

	// Constructors

	/** default constructor */
	public AbstractPayarrearsrec() {
	}

	/** minimal constructor */
	public AbstractPayarrearsrec(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractPayarrearsrec(String id, String pid, String planid, Timestamp addtime, Double repayamount) {
		this.id = id;
		this.pid = pid;
		this.planid = planid;
		this.addtime = addtime;
		this.repayamount = repayamount;
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

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Double getRepayamount() {
		return this.repayamount;
	}

	public void setRepayamount(Double repayamount) {
		this.repayamount = repayamount;
	}

}