package com.ioes.lims.beans;

/**
 * Ratio entity. @author MyEclipse Persistence Tools
 */
public class Ratio extends AbstractRatio implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Ratio() {
	}

	/** minimal constructor */
	public Ratio(String id) {
		super(id);
	}

	/** full constructor */
	public Ratio(String id, Double ratio, String ratiodesc) {
		super(id, ratio, ratiodesc);
	}

}
