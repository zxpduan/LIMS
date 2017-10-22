package com.ioes.lims.beans;

import java.sql.Timestamp;

/**
 * Payarrearsrec entity. @author MyEclipse Persistence Tools
 */
public class Payarrearsrec extends AbstractPayarrearsrec implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Payarrearsrec() {
	}

	/** minimal constructor */
	public Payarrearsrec(String id) {
		super(id);
	}

	/** full constructor */
	public Payarrearsrec(String id, String pid, String planid, Timestamp addtime, Double repayamount) {
		super(id, pid, planid, addtime, repayamount);
	}

}
