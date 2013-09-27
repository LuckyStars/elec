package com.nbcedu.function.booksite.website.pager;


import java.util.List;

@SuppressWarnings("unchecked")
public class PagerModel {
	private List datas;//当前页显示的数据列表
	private int total;//分页显示的所有数据总数
	private int totalPageNo;//总的页面数
	
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the totalPageNo
	 */
	public int getTotalPageNo() {
		return totalPageNo;
	}
	/**
	 * @param totalPageNo the totalPageNo to set
	 */
	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}
	
	
}
