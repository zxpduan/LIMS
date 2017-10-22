package com.ioes.lims.beans;

/**
 * Fun entity. @author MyEclipse Persistence Tools
 */
public class Fun extends AbstractFun implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Fun() {
	}

	/** minimal constructor */
	public Fun(String id) {
		super(id);
	}

	/** full constructor */
	public Fun(String id, String funname, String funcode, String funrul, String fundesc) {
		super(id, funname, funcode, funrul, fundesc);
	}

}
