package com.ioes.lims.beans;

/**
 * AbstractDept entity provides the base persistence definition of the Dept entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDept implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String deptdesc;
	private String parent;

	// Constructors

	/** default constructor */
	public AbstractDept() {
	}

	/** minimal constructor */
	public AbstractDept(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractDept(String id, String name, String deptdesc, String parent) {
		this.id = id;
		this.name = name;
		this.deptdesc = deptdesc;
		this.parent = parent;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptdesc() {
		return this.deptdesc;
	}

	public void setDeptdesc(String deptdesc) {
		this.deptdesc = deptdesc;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}