package com.ioes.lims.beans;

/**
 * AbstractFavorite entity provides the base persistence definition of the Favorite entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractFavorite implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private Integer favcode;
	private String favvalue;

	// Constructors

	/** default constructor */
	public AbstractFavorite() {
	}

	/** minimal constructor */
	public AbstractFavorite(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractFavorite(String id, String userid, Integer favcode, String favvalue) {
		this.id = id;
		this.userid = userid;
		this.favcode = favcode;
		this.favvalue = favvalue;
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

	public Integer getFavcode() {
		return this.favcode;
	}

	public void setFavcode(Integer favcode) {
		this.favcode = favcode;
	}

	public String getFavvalue() {
		return this.favvalue;
	}

	public void setFavvalue(String favvalue) {
		this.favvalue = favvalue;
	}

}