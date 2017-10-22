package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Repayplan entity. @author MyEclipse Persistence Tools
 */
public class Repayplan extends AbstractRepayplan implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Repayplan() {
	}

	/** minimal constructor */
	public Repayplan(String id) {
		super(id);
	}

	/** full constructor */
	public Repayplan(String id, String pid, Integer paytimes, Double payamount, Double cost, Double interest, Timestamp addtime,Date payday) {
		super(id, pid, paytimes, payamount, addtime, cost, interest,payday);
	}

}
