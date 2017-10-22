package com.ioes.lims.idao;

import java.util.LinkedHashMap;
import java.util.List;

import com.ioes.lims.beans.Sysparam;

public interface ISysparamDAO extends IBaseDAO{
	public String merge(Sysparam detachedInstance) ;
	public Sysparam findOnlyOne() ;

	
	
}
