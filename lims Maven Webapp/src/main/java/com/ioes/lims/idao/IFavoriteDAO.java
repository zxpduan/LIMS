package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.LockMode;

import com.ioes.lims.beans.Favorite;
import com.ioes.lims.beans.Fun;
import com.ioes.lims.beans.Role;

public interface IFavoriteDAO extends IBaseDAO{
	public void save(Favorite transientInstance);

	public void delete(Favorite persistentInstance) ;

	public Favorite findById(java.lang.String id) ;

	public List findByExample(Favorite instance) ;


	public List findByProperty(String propertyName, Object value);
	public List findByUserid(Object userid) ;
	public List findByFavcode(Object favcode);
	public List findByFavvalue(Object favvalue) ;

	public List findAll() ;

	public Favorite merge(Favorite detachedInstance) ;

	public void attachDirty(Favorite instance);

	public void attachClean(Favorite instance) ;
}
