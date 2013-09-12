package com.nbcedu.function.elec.biz;

import com.nbcedu.function.elec.devcore.biz.ElecBaseBiz;
import com.nbcedu.function.elec.model.ECTerm;

public interface ECTermBiz extends ElecBaseBiz<ECTerm>{
	/**
	 * 判断此学期课程是否已经开放
	 * (如果开放时间结束也会返回false)
	 * @param term
	 * @return
	 * @author xuechong
	 */
	public Boolean termStart(ECTerm term);
	/**
	 * 查询当前学期<br>
	 * (如果还没有学期则返回null)
	 * @return
	 * @author xuechong
	 */
	public ECTerm findCurrentTerm();
	
}
