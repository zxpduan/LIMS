package com.ioes.lims.beans;

/**
 * Dept entity. @author MyEclipse Persistence Tools
 */
public class Dept extends AbstractDept implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Dept() {
	}

	/** minimal constructor */
	public Dept(String id) {
		super(id);
	}

	/** full constructor */
	public Dept(String id, String name, String desc, String parent) {
		super(id, name, desc, parent);
	}

}
