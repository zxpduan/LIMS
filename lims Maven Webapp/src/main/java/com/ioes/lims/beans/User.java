package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends AbstractUser implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String id) {
		super(id);
	}

	/** full constructor */
	public User(String id, String account, String username, String pw, String dept, Timestamp addtime, Set customers) {
		super(id, account, username, pw, dept, addtime, customers);
	}

}
