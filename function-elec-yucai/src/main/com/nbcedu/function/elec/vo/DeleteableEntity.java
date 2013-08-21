package com.nbcedu.function.elec.vo;

import java.io.Serializable;

public interface DeleteableEntity extends Serializable{
	/**
	 * 删除状态
	 * @return
	 * @author xuechong
	 */
	public abstract Integer getDelState();
	/**
	 * 删除
	 * @param delState
	 * @author xuechong
	 */
	public abstract void setDelState(Integer delState);
	
}
