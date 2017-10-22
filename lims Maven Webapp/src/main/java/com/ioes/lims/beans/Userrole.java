package com.ioes.lims.beans;

/**
 * Userrole entity. @author MyEclipse Persistence Tools
 */
public class Userrole extends AbstractUserrole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Userrole() {
	}

	/** minimal constructor */
	public Userrole(String id) {
		super(id);
	}

	/** full constructor */
	public Userrole(String id, String userid, String roleid) {
		super(id, userid, roleid);
	}

}
