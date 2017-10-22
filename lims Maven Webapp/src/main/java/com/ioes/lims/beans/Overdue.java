package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Overdue entity. @author MyEclipse Persistence Tools
 */
public class Overdue extends AbstractOverdue implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Overdue() {
	}

	/** minimal constructor */
	public Overdue(String id) {
		super(id);
	}

	/** full constructor */
	public Overdue(String id, String pid, String planid, Double amount, Date startdate, Double overinterest, Timestamp addtime, Integer overday, Double payoff, Double repayinterest, Date updatedate) {
		super(id, pid, planid, amount, startdate, overinterest, addtime, overday, payoff, repayinterest, updatedate);
	}

}
