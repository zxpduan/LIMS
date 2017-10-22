package com.ioes.lims.beans;

import java.sql.Timestamp;

/**
 * AbstractProject entity provides the base persistence definition of the Project entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractProject implements java.io.Serializable {

	// Fields

	private String id;
	private String pname;
	private String pcode;
	private String pdesc;
	private Timestamp addtime;
	private Integer serial;

	// Constructors

	/** default constructor */
	public AbstractProject() {
	}

	/** minimal constructor */
	public AbstractProject(String id, Integer serial) {
		this.id = id;
		this.serial = serial;
	}

	/** full constructor */
	public AbstractProject(String id, String pname, String pcode, String pdesc, Timestamp addtime, Integer serial) {
		this.id = id;
		this.pname = pname;
		this.pcode = pcode;
		this.pdesc = pdesc;
		this.addtime = addtime;
		this.serial = serial;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPcode() {
		return this.pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPdesc() {
		return this.pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Integer getSerial() {
		return this.serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}