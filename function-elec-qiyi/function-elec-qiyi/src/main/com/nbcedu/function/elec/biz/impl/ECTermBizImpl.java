package com.nbcedu.function.elec.biz.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.dao.ECTermDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.Constants;

@Repository(value="elecTermBiz")
public class ECTermBizImpl extends ElecBaseBizImpl<ECTerm> implements ECTermBiz{
	private ECTermDao termDao;
	
	@Override
	public void add(ECTerm entity) {
		List<ECTerm> termList = this.termDao.getAll();
		for (ECTerm ecTerm : termList) {
			ecTerm.setCurrentTerm(Boolean.FALSE);
			this.termDao.update(ecTerm);
		}
		entity.setDelState(Constants.DEL_STATE_ENABLED);
		entity.setCreateDate(new Date());
		entity.setCurrentTerm(Boolean.TRUE);
		super.add(entity);
	}
	
	@Override
	public Boolean termStart(ECTerm term) {
		if(term==null){
			return Boolean.FALSE;
		}
		return term.getOpenDateStart().before(new Date())&&term.getOpenDateEnd().after(new Date());
	}
	
	@Override
	public ECTerm findCurrentTerm() {
		return this.termDao.findCurrentTerm();
	}
	
	@Override
	public List<ECTerm> findAll() {
		List<ECTerm> result = super.findAll();
		Collections.sort(result, new Comparator<ECTerm>() {
			@Override
			public int compare(ECTerm o1, ECTerm o2) {
				return o1.getCreateDate().before(o2.getCreateDate()) ? 1 : 
					o1.getCreateDate().equals(o2.getCreateDate()) ? 0 : -1;
			}
		});
		return result;
	}
	///////////////////////////
	/////GETTERS&SETTERS///////
	//////////////////////////
	@Autowired
	public void setTermDao(ECTermDao termDao) {
		this.termDao = termDao;
		this.setElecHibernateBaseDao(termDao);
	}
	
}
