package com.ioes.lims.beans;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
public class Role extends AbstractRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String id) {
		super(id);
	}

	/** full constructor */
	public Role(String id, String rolename, String roledesc) {
		super(id, rolename, roledesc);
	}

}
