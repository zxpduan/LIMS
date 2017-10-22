package com.ioes.lims.beans;

/**
 * AbstractFun entity provides the base persistence definition of the Fun entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractFun implements java.io.Serializable {

	// Fields

	private String id;
	private String funname;
	private String funcode;
	private String funrul;
	private String fundesc;

	// Constructors

	/** default constructor */
	public AbstractFun() {
	}

	/** minimal constructor */
	public AbstractFun(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractFun(String id, String funname, String funcode, String funrul, String fundesc) {
		this.id = id;
		this.funname = funname;
		this.funcode = funcode;
		this.funrul = funrul;
		this.fundesc = fundesc;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunname() {
		return this.funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public String getFuncode() {
		return this.funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getFunrul() {
		return this.funrul;
	}

	public void setFunrul(String funrul) {
		this.funrul = funrul;
	}

	public String getFundesc() {
		return this.fundesc;
	}

	public void setFundesc(String fundesc) {
		this.fundesc = fundesc;
	}

}