package com.nbcedu.function.elec.dao;

import java.util.List;

import com.nbcedu.function.elec.devcore.dao.hibernate.ElecHibernateBaseDao;
import com.nbcedu.function.elec.model.ECSign;

public interface ECSignDao extends ElecHibernateBaseDao<ECSign>{
	/**
	 * 按学生ID查询当前学期学生报名课程
	 * @param uid
	 * @return
	 * @author xuechong
	 */
	public List<ECSign> findByStudentId(String uid);
	
	/**
	 * 统计指定课时已报名人数

	 * @param classhourId
	 * @return
	 * @author xuechong
	 */
	public Integer getcurrentChNum(String classhourId);
	
	/**
	 * 调用存储过程进行报名
	 * @param ECSign
	 * @return
	 */
	public String addSignPro(ECSign ecSign);
}
