package com.ioes.lims.beans;

/**
 * AbstractRolefun entity provides the base persistence definition of the Rolefun entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRolefun implements java.io.Serializable {

	// Fields

	private String id;
	private String roleid;
	private String funid;

	// Constructors

	/** default constructor */
	public AbstractRolefun() {
	}

	/** minimal constructor */
	public AbstractRolefun(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractRolefun(String id, String roleid, String funid) {
		this.id = id;
		this.roleid = roleid;
		this.funid = funid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getFunid() {
		return this.funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

}