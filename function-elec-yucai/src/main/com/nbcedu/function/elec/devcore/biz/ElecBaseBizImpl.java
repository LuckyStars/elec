package com.nbcedu.function.elec.devcore.biz;

import java.io.Serializable;
import java.util.Collection;

import com.nbcedu.core.biz.BaseBizImpl;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDao;

/**
 * BaseBiz
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
public class ElecBaseBizImpl<T extends Serializable> extends BaseBizImpl<T> {
	
	private ElecHibernateBaseDao<T> baseDao;
	
	public void setElecHibernateBaseDao(ElecHibernateBaseDao<T> baseDao) {
		this.baseDao = baseDao;
		super.setBaseDao(baseDao);
	}
	
	
	/**
	 * 保存或更新
	 * @param entity
	 * @return
	 */
	public T addorUpdate(T entity){
		return baseDao.saveOrUpdateEle(entity);
	}
	
	/**
	 * 批量删除
	 * @param entities
	 */
	public void removeAll(Collection<T> entities) {
		baseDao.batchDelete(entities);
	}
	
}
