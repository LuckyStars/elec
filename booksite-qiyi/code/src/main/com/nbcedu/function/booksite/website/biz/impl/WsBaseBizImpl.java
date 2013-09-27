package com.nbcedu.function.booksite.website.biz.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.nbcedu.function.booksite.website.biz.WsBaseBiz;
import com.nbcedu.function.booksite.website.dao.WsBaseDAO;


@SuppressWarnings("unchecked")
public class WsBaseBizImpl<T extends Serializable> implements WsBaseBiz<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	private WsBaseDAO<T> dao;

	public void setDao(WsBaseDAO<T> dao) {
		this.dao = dao;
	}

	protected WsBaseDAO<T> getDao() {
		return this.dao;
	}

	@Transactional(readOnly = true)
	public T findById(Serializable id) {
		return dao.get(id);
	}

	@Transactional(readOnly = true)
	public T load(Serializable id) {
		return dao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return dao.getAll();
	}

/*	@Transactional(readOnly = true)
	public PaginatedListImpl getPagedObject(PaginatedListImpl page,String orderBy, boolean isAsc,
			   Criterion... criterions){
		return dao.getPagedObject(page, orderBy, isAsc, criterions);
	}
	
	@Transactional(readOnly = true)
	public PaginatedListImpl getPagedObject(PaginatedListImpl page,Criterion... criterions){
		return dao.getPagedObject(page, criterions);
	}
*/
	/**
	 * 表里面是否有相同名字的数据
	 * 李斌
	 */
	public boolean getEquesName(Class<T> c,String name)
	{
		try {
			List list=dao.find("from "+c.getName()+" s where s.name=?",name);
			if (list.size()>0) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	 
	public T save(T entity) {
		return (T)dao.save(entity);
	}

	public T saveAndRefresh(T entity) {
		this.save(entity);
		getDao().refresh(entity);
		return entity;
	}

	public Object saveOrUpdate(T o) {
		return dao.saveOrUpdate(o);
	}

	public void delete(T o) {
		dao.remove(o);
	}

	public Object update(T o) {
		return dao.update(o);
	}

	public Object merge(T o) {
		return dao.merge(o);
	}

	public T deleteById(Serializable id) {
		if (id == null) {
			return null;
		}
		return dao.removeById(id);
	}

	public T deleteById(String id) {
		if (id == null) {
			return null;
		}
		return dao.removeById(id);
	}

	public List<T> deleteById(Serializable[] ids) {
		List<T> dts = new ArrayList<T>();
		T del = null;
		if (ids != null && ids.length > 0) {
			for (Serializable id : ids) {
				del = deleteById(id);
				if (del != null) {
					dts.add(del);
				}
			}
		}
		return dts;
	}
	
	public String getIdName(){
		return dao.getIdName();
	}

}
