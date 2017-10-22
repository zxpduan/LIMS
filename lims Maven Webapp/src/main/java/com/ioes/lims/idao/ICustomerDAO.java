package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.Customer;
import com.ioes.lims.beans.User;

public interface ICustomerDAO extends IBaseDAO{
	
	public void delete(Customer persistentInstance);

	public Customer findById(java.lang.String id);

	public List findByExample(Customer instance);

	public List findByProperty(String propertyName, Object value);

	public List findByCname(Object cname) ;
	public List findByCgender(Object cgender);
	public List findByCwed(Object cwed) ;

	public List findByCcert(Object ccert) ;

	public List findByCcertnum(Object ccertnum) ;

	public List findByCeduc(Object ceduc) ;

	public List findByCmobile(Object cmobile);

	public List findByCaddr(Object caddr) ;

	public List findByCresid(Object cresid);

	public List findByCcom(Object ccom) ;

	public List findByCpost(Object cpost);

	public List findByCcomaddr(Object ccomaddr) ;

	public List findByCincome(Object cincome);

	public List findByCcar(Object ccar);

	public List findByCqq(Object cqq) ;

	public List findByCwx(Object cwx);

	public List findByCemail(Object cemail) ;

	public List findByChouseaddr(Object chouseaddr) ;

	public List findByChprice(Object chprice);

	public List findByCcontact1(Object ccontact1);

	public List findByCrealtion1(Object crealtion1);

	public List findByCmobile1(Object cmobile1) ;

	public List findByCcontact2(Object ccontact2);

	public List findByCrealition2(Object crealition2);

	public List findByCmobile2(Object cmobile2) ;

	public List findByCreplace1(Object creplace1) ;

	public List findByCreplace2(Object creplace2) ;

	public List findByCdesc(Object cdesc) ;

	public List findAll() ;

	public String save(Customer transientInstance) ;
	public String merge(Customer detachedInstance) ;
	public List<LinkedHashMap<String, Object>> getCustomerInfo(String firstNameChar);

}
