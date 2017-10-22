package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AbstractRepayrec entity provides the base persistence definition of the Repayrec entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRepayrec implements java.io.Serializable {

	// Fields

	private String id;
	private String pid;
	private Timestamp addtime;
	private Double samount;
	private Double amount;
	private String repid;
	private Date repaydate;
	private Integer instalment;
	private Double cost;
	private Double interest;
	private Double overinterest;
	private Double arrears;
	private Double amountmon;
	private Double payarrears;
	private Integer isprepay;

	// Constructors

	/** default constructor */
	public AbstractRepayrec() {
	}

	/** minimal constructor */
	public AbstractRepayrec(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractRepayrec(String id, String pid, Timestamp addtime, Double samount, Double amount, String repid, Date repaydate, Integer instalment, Double cost, Double interest, Double overinterest, Double arrears, Double amountmon, Double payarrears, Integer isprepay) {
		this.id = id;
		this.pid = pid;
		this.addtime = addtime;
		this.samount = samount;
		this.amount = amount;
		this.repid = repid;
		this.repaydate = repaydate;
		this.instalment = instalment;
		this.cost = cost;
		this.interest = interest;
		this.overinterest = overinterest;
		this.arrears = arrears;
		this.amountmon = amountmon;
		this.payarrears = payarrears;
		this.isprepay = isprepay;
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

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Double getSamount() {
		return this.samount;
	}

	public void setSamount(Double samount) {
		this.samount = samount;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRepid() {
		return this.repid;
	}

	public void setRepid(String repid) {
		this.repid = repid;
	}

	public Date getRepaydate() {
		return this.repaydate;
	}

	public void setRepaydate(Date repaydate) {
		this.repaydate = repaydate;
	}

	public Integer getInstalment() {
		return this.instalment;
	}

	public void setInstalment(Integer instalment) {
		this.instalment = instalment;
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

	public Double getOverinterest() {
		return this.overinterest;
	}

	public void setOverinterest(Double overinterest) {
		this.overinterest = overinterest;
	}

	public Double getArrears() {
		return this.arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getAmountmon() {
		return this.amountmon;
	}

	public void setAmountmon(Double amountmon) {
		this.amountmon = amountmon;
	}

	public Double getPayarrears() {
		return this.payarrears;
	}

	public void setPayarrears(Double payarrears) {
		this.payarrears = payarrears;
	}

	public Integer getIsprepay() {
		return this.isprepay;
	}

	public void setIsprepay(Integer isprepay) {
		this.isprepay = isprepay;
	}

}