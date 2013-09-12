package com.nbcedu.function.elec.dao;

import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDao;
import com.nbcedu.function.elec.model.ECTerm;

public interface ECTermDao extends ElecHibernateBaseDao<ECTerm>{
	/**
	 * 查询当前学期
	 * @return
	 * @author xuechong
	 */
	public ECTerm findCurrentTerm();
}
