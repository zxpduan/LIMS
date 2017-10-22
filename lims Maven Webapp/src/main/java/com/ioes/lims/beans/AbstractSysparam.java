package com.ioes.lims.beans;

/**
 * AbstractSysparam entity provides the base persistence definition of the Sysparam entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSysparam implements java.io.Serializable {

	// Fields

	private String id;
	private Integer overdueday;

	// Constructors

	/** default constructor */
	public AbstractSysparam() {
	}

	/** minimal constructor */
	public AbstractSysparam(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractSysparam(String id, Integer overdueday) {
		this.id = id;
		this.overdueday = overdueday;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOverdueday() {
		return this.overdueday;
	}

	public void setOverdueday(Integer overdueday) {
		this.overdueday = overdueday;
	}

}