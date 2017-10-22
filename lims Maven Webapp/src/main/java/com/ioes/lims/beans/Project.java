package com.ioes.lims.beans;

import java.sql.Timestamp;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */
public class Project extends AbstractProject implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(String id, Integer serial) {
		super(id, serial);
	}

	/** full constructor */
	public Project(String id, String pname, String pcode, String pdesc, Timestamp addtime, Integer serial) {
		super(id, pname, pcode, pdesc, addtime, serial);
	}

}
