package com.ioes.lims.beans;

/**
 * AbstractUserrole entity provides the base persistence definition of the Userrole entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserrole implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private String roleid;

	// Constructors

	/** default constructor */
	public AbstractUserrole() {
	}

	/** minimal constructor */
	public AbstractUserrole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserrole(String id, String userid, String roleid) {
		this.id = id;
		this.userid = userid;
		this.roleid = roleid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}