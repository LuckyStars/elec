package com.nbcedu.function.elec.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.model.ECTerm;

@Controller("termAction")
@Scope("prototype")
public class ECTermAction extends ElecBaseAction {
	
	private static final Logger logger = Logger.getLogger(ECTermAction.class);
	private static final long serialVersionUID = 1L;
	
	@Resource(name="elecTermBiz")
	private ECTermBiz termBiz;
	
	private ECTerm term;
	private List<ECTerm> termList;
	/**
	 * 分页
	 * @return
	 * @author xuechong
	 */
	public String list(){
		this.termList = this.termBiz.findAll();
		return LIST;
	}
	/**
	 * 跳转至新增
	 * @return
	 * @author xuechong
	 */
	public String gotoAdd(){
		return "add";
	}
	/**
	 * 新增
	 * @return
	 * @author xuechong
	 */
	public synchronized String add(){
		this.termBiz.add(term);
		return this.list();
	}
	/**
	 * 更新
	 * @return
	 * @author xuechong
	 */
	public String update(){
		ECTerm ecterm = this.termBiz.findById(this.id);
		BeanUtils.copyProperties(this.term, ecterm, 
				new String[]{"id","createDate","delState","currentTerm"});
		this.termBiz.modify(ecterm);
		return this.list();
	}
	/**
	 * 跳转到编辑
	 * @return
	 * @author xuechong
	 */
	public String edit(){
		this.term = this.termBiz.findById(this.id);
		return "edit";
	}
	/**
	 * 查看详细
	 * @return
	 * @author xuechong
	 */
	public String view(){
		this.term = this.termBiz.findById(this.id);
		return "view";
	}
	//////////////////////////
	/////GETTERS&SETTERS//////
	//////////////////////////
	public ECTerm getTerm() {
		return term;
	}
	public void setTerm(ECTerm term) {
		this.term = term;
	}
	public List<ECTerm> getTermList() {
		return termList;
	}
	public void setTermList(List<ECTerm> termList) {
		this.termList = termList;
	}
	
}
