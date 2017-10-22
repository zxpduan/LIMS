package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AbstractPact entity provides the base persistence definition of the Pact entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPact implements java.io.Serializable {

	// Fields

	private String id;
	private String cttcustomer;
	private Date cttdate;
	private String cttproject;
	private Integer cttdiscount;
	private Integer cttserial;
	private String cttcode;
	private String cttbank;
	private String cttbankcard;
	private Double cttamount;
	private String cttamountu;
	private Integer cttcate;
	private String cttratio;
	private Integer cttrepaycate;
	private Integer cttfun;
	private Double cttprice;
	private Double cttselfprice;
	private Date cttstart;
	private Date cttloadenddate;
	private Date cttloandate;
	private Date cttrepayday;
	private String cttcarloc;
	private String ctthouseloc;
	private String operator;
	private Timestamp addtime;
	private Integer auditstate;
	private Double cttbalance;
	private Double cttpaymonth;
	private Integer ctttta;
	private Double ctttaa;
	private Integer ctthpaytimes;
	private Integer cttspaytimes;
	private Double cttnowcost;
	private Double cttnowcosted;
	private Double cttnowinterest;
	private Double cttnowinterested;
	private Date nextrepayday;
	private Integer calculatecate;

	// Constructors

	/** default constructor */
	public AbstractPact() {
	}

	/** minimal constructor */
	public AbstractPact(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractPact(String id, String cttcustomer, Date cttdate, String cttproject, Integer cttdiscount, Integer cttserial, String cttcode, String cttbank, String cttbankcard, Double cttamount, String cttamountu, Integer cttcate, String cttratio, Integer cttrepaycate, Integer cttfun, Double cttprice, Double cttselfprice, Date cttstart,
			Date cttloadenddate, Date cttloandate, Date cttrepayday, String cttcarloc, String ctthouseloc, String operator, Timestamp addtime, Integer auditstate, Double cttbalance, Double cttpaymonth, Integer ctttta, Double ctttaa, Integer ctthpaytimes, Integer cttspaytimes, Double cttnowcost, Double cttnowcosted, Double cttnowinterest,
			Double cttnowinterested, Date nextrepayday, Integer calculatecate) {
		this.id = id;
		this.cttcustomer = cttcustomer;
		this.cttdate = cttdate;
		this.cttproject = cttproject;
		this.cttdiscount = cttdiscount;
		this.cttserial = cttserial;
		this.cttcode = cttcode;
		this.cttbank = cttbank;
		this.cttbankcard = cttbankcard;
		this.cttamount = cttamount;
		this.cttamountu = cttamountu;
		this.cttcate = cttcate;
		this.cttratio = cttratio;
		this.cttrepaycate = cttrepaycate;
		this.cttfun = cttfun;
		this.cttprice = cttprice;
		this.cttselfprice = cttselfprice;
		this.cttstart = cttstart;
		this.cttloadenddate = cttloadenddate;
		this.cttloandate = cttloandate;
		this.cttrepayday = cttrepayday;
		this.cttcarloc = cttcarloc;
		this.ctthouseloc = ctthouseloc;
		this.operator = operator;
		this.addtime = addtime;
		this.auditstate = auditstate;
		this.cttbalance = cttbalance;
		this.cttpaymonth = cttpaymonth;
		this.ctttta = ctttta;
		this.ctttaa = ctttaa;
		this.ctthpaytimes = ctthpaytimes;
		this.cttspaytimes = cttspaytimes;
		this.cttnowcost = cttnowcost;
		this.cttnowcosted = cttnowcosted;
		this.cttnowinterest = cttnowinterest;
		this.cttnowinterested = cttnowinterested;
		this.nextrepayday = nextrepayday;
		this.calculatecate = calculatecate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCttcustomer() {
		return this.cttcustomer;
	}

	public void setCttcustomer(String cttcustomer) {
		this.cttcustomer = cttcustomer;
	}

	public Date getCttdate() {
		return this.cttdate;
	}

	public void setCttdate(Date cttdate) {
		this.cttdate = cttdate;
	}

	public String getCttproject() {
		return this.cttproject;
	}

	public void setCttproject(String cttproject) {
		this.cttproject = cttproject;
	}

	public Integer getCttdiscount() {
		return this.cttdiscount;
	}

	public void setCttdiscount(Integer cttdiscount) {
		this.cttdiscount = cttdiscount;
	}

	public Integer getCttserial() {
		return this.cttserial;
	}

	public void setCttserial(Integer cttserial) {
		this.cttserial = cttserial;
	}

	public String getCttcode() {
		return this.cttcode;
	}

	public void setCttcode(String cttcode) {
		this.cttcode = cttcode;
	}

	public String getCttbank() {
		return this.cttbank;
	}

	public void setCttbank(String cttbank) {
		this.cttbank = cttbank;
	}

	public String getCttbankcard() {
		return this.cttbankcard;
	}

	public void setCttbankcard(String cttbankcard) {
		this.cttbankcard = cttbankcard;
	}

	public Double getCttamount() {
		return this.cttamount;
	}

	public void setCttamount(Double cttamount) {
		this.cttamount = cttamount;
	}

	public String getCttamountu() {
		return this.cttamountu;
	}

	public void setCttamountu(String cttamountu) {
		this.cttamountu = cttamountu;
	}

	public Integer getCttcate() {
		return this.cttcate;
	}

	public void setCttcate(Integer cttcate) {
		this.cttcate = cttcate;
	}

	public String getCttratio() {
		return this.cttratio;
	}

	public void setCttratio(String cttratio) {
		this.cttratio = cttratio;
	}

	public Integer getCttrepaycate() {
		return this.cttrepaycate;
	}

	public void setCttrepaycate(Integer cttrepaycate) {
		this.cttrepaycate = cttrepaycate;
	}

	public Integer getCttfun() {
		return this.cttfun;
	}

	public void setCttfun(Integer cttfun) {
		this.cttfun = cttfun;
	}

	public Double getCttprice() {
		return this.cttprice;
	}

	public void setCttprice(Double cttprice) {
		this.cttprice = cttprice;
	}

	public Double getCttselfprice() {
		return this.cttselfprice;
	}

	public void setCttselfprice(Double cttselfprice) {
		this.cttselfprice = cttselfprice;
	}

	public Date getCttstart() {
		return this.cttstart;
	}

	public void setCttstart(Date cttstart) {
		this.cttstart = cttstart;
	}

	public Date getCttloadenddate() {
		return this.cttloadenddate;
	}

	public void setCttloadenddate(Date cttloadenddate) {
		this.cttloadenddate = cttloadenddate;
	}

	public Date getCttloandate() {
		return this.cttloandate;
	}

	public void setCttloandate(Date cttloandate) {
		this.cttloandate = cttloandate;
	}

	public Date getCttrepayday() {
		return this.cttrepayday;
	}

	public void setCttrepayday(Date cttrepayday) {
		this.cttrepayday = cttrepayday;
	}

	public String getCttcarloc() {
		return this.cttcarloc;
	}

	public void setCttcarloc(String cttcarloc) {
		this.cttcarloc = cttcarloc;
	}

	public String getCtthouseloc() {
		return this.ctthouseloc;
	}

	public void setCtthouseloc(String ctthouseloc) {
		this.ctthouseloc = ctthouseloc;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Integer getAuditstate() {
		return this.auditstate;
	}

	public void setAuditstate(Integer auditstate) {
		this.auditstate = auditstate;
	}

	public Double getCttbalance() {
		return this.cttbalance;
	}

	public void setCttbalance(Double cttbalance) {
		this.cttbalance = cttbalance;
	}

	public Double getCttpaymonth() {
		return this.cttpaymonth;
	}

	public void setCttpaymonth(Double cttpaymonth) {
		this.cttpaymonth = cttpaymonth;
	}

	public Integer getCtttta() {
		return this.ctttta;
	}

	public void setCtttta(Integer ctttta) {
		this.ctttta = ctttta;
	}

	public Double getCtttaa() {
		return this.ctttaa;
	}

	public void setCtttaa(Double ctttaa) {
		this.ctttaa = ctttaa;
	}

	public Integer getCtthpaytimes() {
		return this.ctthpaytimes;
	}

	public void setCtthpaytimes(Integer ctthpaytimes) {
		this.ctthpaytimes = ctthpaytimes;
	}

	public Integer getCttspaytimes() {
		return this.cttspaytimes;
	}

	public void setCttspaytimes(Integer cttspaytimes) {
		this.cttspaytimes = cttspaytimes;
	}

	public Double getCttnowcost() {
		return this.cttnowcost;
	}

	public void setCttnowcost(Double cttnowcost) {
		this.cttnowcost = cttnowcost;
	}

	public Double getCttnowcosted() {
		return this.cttnowcosted;
	}

	public void setCttnowcosted(Double cttnowcosted) {
		this.cttnowcosted = cttnowcosted;
	}

	public Double getCttnowinterest() {
		return this.cttnowinterest;
	}

	public void setCttnowinterest(Double cttnowinterest) {
		this.cttnowinterest = cttnowinterest;
	}

	public Double getCttnowinterested() {
		return this.cttnowinterested;
	}

	public void setCttnowinterested(Double cttnowinterested) {
		this.cttnowinterested = cttnowinterested;
	}

	public Date getNextrepayday() {
		return this.nextrepayday;
	}

	public void setNextrepayday(Date nextrepayday) {
		this.nextrepayday = nextrepayday;
	}

	public Integer getCalculatecate() {
		return this.calculatecate;
	}

	public void setCalculatecate(Integer calculatecate) {
		this.calculatecate = calculatecate;
	}

}