package com.ioes.lims.beans;

/**
 * Sysparam entity. @author MyEclipse Persistence Tools
 */
public class Sysparam extends AbstractSysparam implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Sysparam() {
	}

	/** minimal constructor */
	public Sysparam(String id) {
		super(id);
	}

	/** full constructor */
	public Sysparam(String id, Integer overdueday) {
		super(id, overdueday);
	}

}
