package com.nbcedu.function.elec.dao.impl;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.dao.ECTermDao;
import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDaoImpl;
import com.nbcedu.function.elec.model.ECTerm;
@Repository(value="elecTermDao")
public class ECTermDaoImpl extends ElecHibernateBaseDaoImpl<ECTerm> implements ECTermDao{
	/**
	 * 查询当前学期
	 * @return
	 * @author xuechong
	 */
	@Override
	public ECTerm findCurrentTerm(){
		
		return (ECTerm) this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = session.createCriteria(ECTerm.class);
				cri.add(Restrictions.eq("currentTerm", Boolean.TRUE));
				return cri.uniqueResult();
			}
		});
	}
}
