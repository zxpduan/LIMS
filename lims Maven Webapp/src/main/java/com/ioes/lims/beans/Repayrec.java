package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Repayrec entity. @author MyEclipse Persistence Tools
 */
public class Repayrec extends AbstractRepayrec implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Repayrec() {
	}

	/** minimal constructor */
	public Repayrec(String id) {
		super(id);
	}

	/** full constructor */
	public Repayrec(String id, String pid, Timestamp addtime, Double samount, Double amount, String repid, Date repaydate, Integer instalment, Double cost, Double interest, Double overinterest, Double arrears, Double amountmon, Double payarrears, Integer isprepay) {
		super(id, pid, addtime, samount, amount, repid, repaydate, instalment, cost, interest, overinterest, arrears, amountmon, payarrears, isprepay);
	}

}
