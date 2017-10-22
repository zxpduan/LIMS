package com.ioes.lims.beans;

/**
 * AbstractRole entity provides the base persistence definition of the Role entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRole implements java.io.Serializable {

	// Fields

	private String id;
	private String rolename;
	private String roledesc;

	// Constructors

	/** default constructor */
	public AbstractRole() {
	}

	/** minimal constructor */
	public AbstractRole(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractRole(String id, String rolename, String roledesc) {
		this.id = id;
		this.rolename = rolename;
		this.roledesc = roledesc;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

}