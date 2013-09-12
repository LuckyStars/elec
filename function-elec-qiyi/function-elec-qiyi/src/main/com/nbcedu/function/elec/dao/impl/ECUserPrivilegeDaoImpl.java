package com.nbcedu.function.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.dao.ECUserPrivilegeDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECUserPrivilege;
@Repository("ecUserPrivilegeDao")
public class ECUserPrivilegeDaoImpl extends ElecHibernateBaseDaoImpl<ECUserPrivilege> implements ECUserPrivilegeDao {

}
