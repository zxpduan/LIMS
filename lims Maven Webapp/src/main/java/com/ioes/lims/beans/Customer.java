package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */
public class Customer extends AbstractCustomer implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String id) {
		super(id);
	}

	/** full constructor */
	public Customer(String id, User user, String customername, Integer customergender, Date customerbirth, Integer customerwed, Integer customercert, String customercertnum, Integer customereduc, String customermobile, String customeraddr, Integer customerresid, String customercom, Integer customerpost, String customercomaddr, Double customerincome,
			Integer customercar, String customerqq, String customerwx, String customeremail, String customerhouseaddr, Double customerhprice, String customercontact1, Integer customerrealtion1, String customermobile1, String customercontact2, Integer customerrealition2, String customermobile2, String customerreplace1, String customerreplace2,
			String customerdesc, Timestamp addtime, String customertname, String customertgender, Date customertbirth, Integer customertwed, Integer customertcert, String customertcertnum, Integer customerteduc, String customertmobile, String customertaddr, Integer customertresid, String customertcom, Integer customertpost, String customertcomaddr,
			Double customertincome, Integer customertcar, String customertqq, String customertwx, String customertemail) {
		super(id, user, customername, customergender, customerbirth, customerwed, customercert, customercertnum, customereduc, customermobile, customeraddr, customerresid, customercom, customerpost, customercomaddr, customerincome, customercar, customerqq, customerwx, customeremail, customerhouseaddr, customerhprice, customercontact1, customerrealtion1,
				customermobile1, customercontact2, customerrealition2, customermobile2, customerreplace1, customerreplace2, customerdesc, addtime, customertname, customertgender, customertbirth, customertwed, customertcert, customertcertnum, customerteduc, customertmobile, customertaddr, customertresid, customertcom, customertpost, customertcomaddr,
				customertincome, customertcar, customertqq, customertwx, customertemail);
	}

}
