package com.nbcedu.function.elec.devcore.biz;

import java.io.Serializable;
import java.util.Collection;

import com.nbcedu.core.biz.BaseBiz;

/**
 * BaseBiz基类
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
public interface ElecBaseBiz<T extends Serializable> extends BaseBiz<T> {
	
	/**
	 * 保存或更新
	 * @param entity
	 * @return
	 */
	public T addorUpdate(T entity);
	
	
	/**
	 * 批量删除
	 */
	public void removeAll(Collection<T> entities);
}
