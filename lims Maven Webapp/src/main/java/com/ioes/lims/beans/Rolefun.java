package com.ioes.lims.beans;

/**
 * Rolefun entity. @author MyEclipse Persistence Tools
 */
public class Rolefun extends AbstractRolefun implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Rolefun() {
	}

	/** minimal constructor */
	public Rolefun(String id) {
		super(id);
	}

	/** full constructor */
	public Rolefun(String id, String roleid, String funid) {
		super(id, roleid, funid);
	}

}
