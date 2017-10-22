package com.ioes.lims.beans;

/**
 * AbstractRatio entity provides the base persistence definition of the Ratio entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRatio implements java.io.Serializable {

	// Fields

	private String id;
	private Double ratio;
	private String ratiodesc;

	// Constructors

	/** default constructor */
	public AbstractRatio() {
	}

	/** minimal constructor */
	public AbstractRatio(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractRatio(String id, Double ratio, String ratiodesc) {
		this.id = id;
		this.ratio = ratio;
		this.ratiodesc = ratiodesc;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getRatio() {
		return this.ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public String getRatiodesc() {
		return this.ratiodesc;
	}

	public void setRatiodesc(String ratiodesc) {
		this.ratiodesc = ratiodesc;
	}

}