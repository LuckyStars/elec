package com.nbcedu.function.elec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.dao.ECPlaceDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECPlace;

@Repository("ecPlaceDao")
public class ECPlaceDaoImpl extends ElecHibernateBaseDaoImpl<ECPlace> implements ECPlaceDao , ElecHibernateBaseDao<ECPlace>{

	@SuppressWarnings("unchecked")
	@Override
	public List<ECPlace> findAllPlace(Page page ) {
		StringBuffer sb = new StringBuffer();
		sb.append("  FROM ECPlace ep WHERE 1 = 1  ORDER BY ep.createTime DESC");
		return (List<ECPlace>) this.findPage(page, sb.toString() ).getDatas();
	}

	@Override
	public List<ECPlace> findPlace(String typeId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM ECPlace ep WHERE ep.delState = 0 ");
		List<Object> params = new ArrayList<Object>();
		if(!"".equals(typeId)){
			sb.append(" AND   ep.typeName  like  ? ");
			params.add("%"+typeId+"%");
			 return this.find(sb.toString(), params.toArray());
		}else{
			return this.find(sb.toString());
					
		}
		
		//String hql = " FROM　 ECPlace ep　WHERE ep.delState = 0 AND  ep.typeName  like  ?　";
	}
	
	@Override
	public ECPlace findPlace(ECPlace ecPlace) {
		ECPlace ep = this.get(ecPlace.getId());
		return ep;
	}

	@Override
	public List<ECPlace> findByName(String name) {
		String hql = " FROM ECPlace ep WHERE ep.name = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(name);
		return this.find(hql, params.toArray());
	}

	@Override
	public void addPlace(ECPlace ecPlace) {
		this.saveEle(ecPlace);
	}

	@Override
	public boolean modifyPlace(String id , Integer status) {
		String hql = "FROM ECPlace ep WHERE ep.id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		ECPlace ecp = (ECPlace) this.find(hql, params.toArray()).get(0); 
		ecp.setDelState(status);
		ecp = this.saveOrUpdateEle(ecp);
		if(ecp==null){
			return false; 
		}
		return true;
	}

	@Override
	public void updatePlace(ECPlace ecPlace) {
		this.saveOrUpdateEle(ecPlace);
	}
}
