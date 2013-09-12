package com.nbcedu.function.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.dao.ECClasshourDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECClasshour;

/**
 * 课时
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Repository
public class ECClasshourDaoImpl extends ElecHibernateBaseDaoImpl<ECClasshour> implements ECClasshourDao {

}
