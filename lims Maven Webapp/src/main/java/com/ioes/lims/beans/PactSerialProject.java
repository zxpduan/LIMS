package com.ioes.lims.beans;

import java.sql.Timestamp;

public class PactSerialProject {
	private String id;
	private String pname;
	private String pcode;
	private String pdesc;
	private Timestamp addtime;
	private int serial;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the pcode
	 */
	public String getPcode() {
		return pcode;
	}
	/**
	 * @param pcode the pcode to set
	 */
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	/**
	 * @return the pdesc
	 */
	public String getPdesc() {
		return pdesc;
	}
	/**
	 * @param pdesc the pdesc to set
	 */
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	/**
	 * @return the addtime
	 */
	public Timestamp getAddtime() {
		return addtime;
	}
	/**
	 * @param addtime the addtime to set
	 */
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	/**
	 * @return the serial
	 */
	public int getSerial() {
		return serial;
	}
	/**
	 * @param serial the serial to set
	 */
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public PactSerialProject(String id, String pname, String pcode, String pdesc, Timestamp addtime,int maxserial) {
		this.id = id;
		this.pname = pname;
		this.pcode = pcode;
		this.pdesc = pdesc;
		this.addtime = addtime;
		this.serial=maxserial;
	}
}
