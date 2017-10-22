package com.ioes.lims.beans;

/**
 * Favorite entity. @author MyEclipse Persistence Tools
 */
public class Favorite extends AbstractFavorite implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Favorite() {
	}

	/** minimal constructor */
	public Favorite(String id) {
		super(id);
	}

	/** full constructor */
	public Favorite(String id, String userid, Integer favcode, String favvalue) {
		super(id, userid, favcode, favvalue);
	}

}
