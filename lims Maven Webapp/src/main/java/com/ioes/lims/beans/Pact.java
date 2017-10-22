package com.ioes.lims.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Pact entity. @author MyEclipse Persistence Tools
 */
public class Pact extends AbstractPact implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Pact() {
	}

	/** minimal constructor */
	public Pact(String id) {
		super(id);
	}

	/** full constructor */
	public Pact(String id, String cttcustomer, Date cttdate, String cttproject, Integer cttdiscount, Integer cttserial, String cttcode, String cttbank, String cttbankcard, Double cttamount, String cttamountu, Integer cttcate, String cttratio, Integer cttrepaycate, Integer cttfun, Double cttprice, Double cttselfprice, Date cttstart, Date cttloadenddate,
			Date cttloandate, Date cttrepayday, String cttcarloc, String ctthouseloc, String operator, Timestamp addtime, Integer auditstate, Double cttbalance, Double cttpaymonth, Integer ctttta, Double ctttaa, Integer ctthpaytimes, Integer cttspaytimes, Double cttnowcost, Double cttnowcosted, Double cttnowinterest, Double cttnowinterested,
			Date nextrepayday, Integer calculatecate) {
		super(id, cttcustomer, cttdate, cttproject, cttdiscount, cttserial, cttcode, cttbank, cttbankcard, cttamount, cttamountu, cttcate, cttratio, cttrepaycate, cttfun, cttprice, cttselfprice, cttstart, cttloadenddate, cttloandate, cttrepayday, cttcarloc, ctthouseloc, operator, addtime, auditstate, cttbalance, cttpaymonth, ctttta, ctttaa, ctthpaytimes,
				cttspaytimes, cttnowcost, cttnowcosted, cttnowinterest, cttnowinterested, nextrepayday, calculatecate);
	}

}
