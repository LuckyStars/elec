package com.nbcedu.function.booksite.website.biz;


import java.io.Serializable;
import java.util.List;

public interface WsBaseBiz<T extends Serializable> {
	
	public boolean getEquesName(Class<T> c,String name);
	
	public T findById(Serializable id) ;
	
	public T load(Serializable id) ;

	public List<T> findAll() ;

	public T save(T entity) ;

	public T saveAndRefresh(T entity);

	public Object saveOrUpdate(T o);

	public void delete(T o) ;

	public Object update(T o) ;

	public Object merge(T o) ;

	public T deleteById(Serializable id);
	
	public T deleteById(String id);

	public List<T> deleteById(Serializable[] ids) ;
	
	public String getIdName() ;
		
}
