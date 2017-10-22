package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AbstractCustomer entity provides the base persistence definition of the Customer entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCustomer implements java.io.Serializable {

	// Fields

	private String id;
	private User user;
	private String customername;
	private Integer customergender;
	private Date customerbirth;
	private Integer customerwed;
	private Integer customercert;
	private String customercertnum;
	private Integer customereduc;
	private String customermobile;
	private String customeraddr;
	private Integer customerresid;
	private String customercom;
	private Integer customerpost;
	private String customercomaddr;
	private Double customerincome;
	private Integer customercar;
	private String customerqq;
	private String customerwx;
	private String customeremail;
	private String customerhouseaddr;
	private Double customerhprice;
	private String customercontact1;
	private Integer customerrealtion1;
	private String customermobile1;
	private String customercontact2;
	private Integer customerrealition2;
	private String customermobile2;
	private String customerreplace1;
	private String customerreplace2;
	private String customerdesc;
	private Timestamp addtime;
	private String customertname;
	private String customertgender;
	private Date customertbirth;
	private Integer customertwed;
	private Integer customertcert;
	private String customertcertnum;
	private Integer customerteduc;
	private String customertmobile;
	private String customertaddr;
	private Integer customertresid;
	private String customertcom;
	private Integer customertpost;
	private String customertcomaddr;
	private Double customertincome;
	private Integer customertcar;
	private String customertqq;
	private String customertwx;
	private String customertemail;

	// Constructors

	/** default constructor */
	public AbstractCustomer() {
	}

	/** minimal constructor */
	public AbstractCustomer(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractCustomer(String id, User user, String customername, Integer customergender, Date customerbirth, Integer customerwed, Integer customercert, String customercertnum, Integer customereduc, String customermobile, String customeraddr, Integer customerresid, String customercom, Integer customerpost, String customercomaddr, Double customerincome,
			Integer customercar, String customerqq, String customerwx, String customeremail, String customerhouseaddr, Double customerhprice, String customercontact1, Integer customerrealtion1, String customermobile1, String customercontact2, Integer customerrealition2, String customermobile2, String customerreplace1, String customerreplace2,
			String customerdesc, Timestamp addtime, String customertname, String customertgender, Date customertbirth, Integer customertwed, Integer customertcert, String customertcertnum, Integer customerteduc, String customertmobile, String customertaddr, Integer customertresid, String customertcom, Integer customertpost, String customertcomaddr,
			Double customertincome, Integer customertcar, String customertqq, String customertwx, String customertemail) {
		this.id = id;
		this.user = user;
		this.customername = customername;
		this.customergender = customergender;
		this.customerbirth = customerbirth;
		this.customerwed = customerwed;
		this.customercert = customercert;
		this.customercertnum = customercertnum;
		this.customereduc = customereduc;
		this.customermobile = customermobile;
		this.customeraddr = customeraddr;
		this.customerresid = customerresid;
		this.customercom = customercom;
		this.customerpost = customerpost;
		this.customercomaddr = customercomaddr;
		this.customerincome = customerincome;
		this.customercar = customercar;
		this.customerqq = customerqq;
		this.customerwx = customerwx;
		this.customeremail = customeremail;
		this.customerhouseaddr = customerhouseaddr;
		this.customerhprice = customerhprice;
		this.customercontact1 = customercontact1;
		this.customerrealtion1 = customerrealtion1;
		this.customermobile1 = customermobile1;
		this.customercontact2 = customercontact2;
		this.customerrealition2 = customerrealition2;
		this.customermobile2 = customermobile2;
		this.customerreplace1 = customerreplace1;
		this.customerreplace2 = customerreplace2;
		this.customerdesc = customerdesc;
		this.addtime = addtime;
		this.customertname = customertname;
		this.customertgender = customertgender;
		this.customertbirth = customertbirth;
		this.customertwed = customertwed;
		this.customertcert = customertcert;
		this.customertcertnum = customertcertnum;
		this.customerteduc = customerteduc;
		this.customertmobile = customertmobile;
		this.customertaddr = customertaddr;
		this.customertresid = customertresid;
		this.customertcom = customertcom;
		this.customertpost = customertpost;
		this.customertcomaddr = customertcomaddr;
		this.customertincome = customertincome;
		this.customertcar = customertcar;
		this.customertqq = customertqq;
		this.customertwx = customertwx;
		this.customertemail = customertemail;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public Integer getCustomergender() {
		return this.customergender;
	}

	public void setCustomergender(Integer customergender) {
		this.customergender = customergender;
	}

	public Date getCustomerbirth() {
		return this.customerbirth;
	}

	public void setCustomerbirth(Date customerbirth) {
		this.customerbirth = customerbirth;
	}

	public Integer getCustomerwed() {
		return this.customerwed;
	}

	public void setCustomerwed(Integer customerwed) {
		this.customerwed = customerwed;
	}

	public Integer getCustomercert() {
		return this.customercert;
	}

	public void setCustomercert(Integer customercert) {
		this.customercert = customercert;
	}

	public String getCustomercertnum() {
		return this.customercertnum;
	}

	public void setCustomercertnum(String customercertnum) {
		this.customercertnum = customercertnum;
	}

	public Integer getCustomereduc() {
		return this.customereduc;
	}

	public void setCustomereduc(Integer customereduc) {
		this.customereduc = customereduc;
	}

	public String getCustomermobile() {
		return this.customermobile;
	}

	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}

	public String getCustomeraddr() {
		return this.customeraddr;
	}

	public void setCustomeraddr(String customeraddr) {
		this.customeraddr = customeraddr;
	}

	public Integer getCustomerresid() {
		return this.customerresid;
	}

	public void setCustomerresid(Integer customerresid) {
		this.customerresid = customerresid;
	}

	public String getCustomercom() {
		return this.customercom;
	}

	public void setCustomercom(String customercom) {
		this.customercom = customercom;
	}

	public Integer getCustomerpost() {
		return this.customerpost;
	}

	public void setCustomerpost(Integer customerpost) {
		this.customerpost = customerpost;
	}

	public String getCustomercomaddr() {
		return this.customercomaddr;
	}

	public void setCustomercomaddr(String customercomaddr) {
		this.customercomaddr = customercomaddr;
	}

	public Double getCustomerincome() {
		return this.customerincome;
	}

	public void setCustomerincome(Double customerincome) {
		this.customerincome = customerincome;
	}

	public Integer getCustomercar() {
		return this.customercar;
	}

	public void setCustomercar(Integer customercar) {
		this.customercar = customercar;
	}

	public String getCustomerqq() {
		return this.customerqq;
	}

	public void setCustomerqq(String customerqq) {
		this.customerqq = customerqq;
	}

	public String getCustomerwx() {
		return this.customerwx;
	}

	public void setCustomerwx(String customerwx) {
		this.customerwx = customerwx;
	}

	public String getCustomeremail() {
		return this.customeremail;
	}

	public void setCustomeremail(String customeremail) {
		this.customeremail = customeremail;
	}

	public String getCustomerhouseaddr() {
		return this.customerhouseaddr;
	}

	public void setCustomerhouseaddr(String customerhouseaddr) {
		this.customerhouseaddr = customerhouseaddr;
	}

	public Double getCustomerhprice() {
		return this.customerhprice;
	}

	public void setCustomerhprice(Double customerhprice) {
		this.customerhprice = customerhprice;
	}

	public String getCustomercontact1() {
		return this.customercontact1;
	}

	public void setCustomercontact1(String customercontact1) {
		this.customercontact1 = customercontact1;
	}

	public Integer getCustomerrealtion1() {
		return this.customerrealtion1;
	}

	public void setCustomerrealtion1(Integer customerrealtion1) {
		this.customerrealtion1 = customerrealtion1;
	}

	public String getCustomermobile1() {
		return this.customermobile1;
	}

	public void setCustomermobile1(String customermobile1) {
		this.customermobile1 = customermobile1;
	}

	public String getCustomercontact2() {
		return this.customercontact2;
	}

	public void setCustomercontact2(String customercontact2) {
		this.customercontact2 = customercontact2;
	}

	public Integer getCustomerrealition2() {
		return this.customerrealition2;
	}

	public void setCustomerrealition2(Integer customerrealition2) {
		this.customerrealition2 = customerrealition2;
	}

	public String getCustomermobile2() {
		return this.customermobile2;
	}

	public void setCustomermobile2(String customermobile2) {
		this.customermobile2 = customermobile2;
	}

	public String getCustomerreplace1() {
		return this.customerreplace1;
	}

	public void setCustomerreplace1(String customerreplace1) {
		this.customerreplace1 = customerreplace1;
	}

	public String getCustomerreplace2() {
		return this.customerreplace2;
	}

	public void setCustomerreplace2(String customerreplace2) {
		this.customerreplace2 = customerreplace2;
	}

	public String getCustomerdesc() {
		return this.customerdesc;
	}

	public void setCustomerdesc(String customerdesc) {
		this.customerdesc = customerdesc;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public String getCustomertname() {
		return this.customertname;
	}

	public void setCustomertname(String customertname) {
		this.customertname = customertname;
	}

	public String getCustomertgender() {
		return this.customertgender;
	}

	public void setCustomertgender(String customertgender) {
		this.customertgender = customertgender;
	}

	public Date getCustomertbirth() {
		return this.customertbirth;
	}

	public void setCustomertbirth(Date customertbirth) {
		this.customertbirth = customertbirth;
	}

	public Integer getCustomertwed() {
		return this.customertwed;
	}

	public void setCustomertwed(Integer customertwed) {
		this.customertwed = customertwed;
	}

	public Integer getCustomertcert() {
		return this.customertcert;
	}

	public void setCustomertcert(Integer customertcert) {
		this.customertcert = customertcert;
	}

	public String getCustomertcertnum() {
		return this.customertcertnum;
	}

	public void setCustomertcertnum(String customertcertnum) {
		this.customertcertnum = customertcertnum;
	}

	public Integer getCustomerteduc() {
		return this.customerteduc;
	}

	public void setCustomerteduc(Integer customerteduc) {
		this.customerteduc = customerteduc;
	}

	public String getCustomertmobile() {
		return this.customertmobile;
	}

	public void setCustomertmobile(String customertmobile) {
		this.customertmobile = customertmobile;
	}

	public String getCustomertaddr() {
		return this.customertaddr;
	}

	public void setCustomertaddr(String customertaddr) {
		this.customertaddr = customertaddr;
	}

	public Integer getCustomertresid() {
		return this.customertresid;
	}

	public void setCustomertresid(Integer customertresid) {
		this.customertresid = customertresid;
	}

	public String getCustomertcom() {
		return this.customertcom;
	}

	public void setCustomertcom(String customertcom) {
		this.customertcom = customertcom;
	}

	public Integer getCustomertpost() {
		return this.customertpost;
	}

	public void setCustomertpost(Integer customertpost) {
		this.customertpost = customertpost;
	}

	public String getCustomertcomaddr() {
		return this.customertcomaddr;
	}

	public void setCustomertcomaddr(String customertcomaddr) {
		this.customertcomaddr = customertcomaddr;
	}

	public Double getCustomertincome() {
		return this.customertincome;
	}

	public void setCustomertincome(Double customertincome) {
		this.customertincome = customertincome;
	}

	public Integer getCustomertcar() {
		return this.customertcar;
	}

	public void setCustomertcar(Integer customertcar) {
		this.customertcar = customertcar;
	}

	public String getCustomertqq() {
		return this.customertqq;
	}

	public void setCustomertqq(String customertqq) {
		this.customertqq = customertqq;
	}

	public String getCustomertwx() {
		return this.customertwx;
	}

	public void setCustomertwx(String customertwx) {
		this.customertwx = customertwx;
	}

	public String getCustomertemail() {
		return this.customertemail;
	}

	public void setCustomertemail(String customertemail) {
		this.customertemail = customertemail;
	}

}