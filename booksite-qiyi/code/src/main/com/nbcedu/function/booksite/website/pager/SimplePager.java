package com.nbcedu.function.booksite.website.pager;


import java.util.List;

/**
 * <p>
 * 简单分页模型
 * </p>
 * 
 * @author Du Haiying Create at:2011-10-24 下午04:11:31
 */
public class SimplePager<T> {

	/**
	 * 分页数据列表
	 */
	private List<T> datas;

	/**
	 * 每页显示数据个数
	 */
	private int pageSize = 1;

	/**
	 * 当前页码
	 */
	private int pageNo = 1;

	/**
	 * 最大页码（总的页面个数）
	 */
	private int maxPageNo;

	/**
	 * 数据总的个数
	 */
	private int count;

	/**
	 * default
	 */
	public SimplePager() {

	}

	/**
	 * 指定 要查找的当前页码与页面显示数据个数
	 * 
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示数据个数
	 */
	public SimplePager(int pageNo, int pageSize) {
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		if (pageSize <= 0) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
		}

	}

	/**
	 * 指定 要查找的当前页码与页面显示数据个数
	 * 
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示数据个数
	 */
	public SimplePager(String pageNo, int pageSize) {
		int t = 0;
		try {
			t = Integer.parseInt(pageNo);
		} catch (NumberFormatException e) {
			t = 1;
		}

		if (t <= 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = t;
		}
		if (pageSize <= 0) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
		}

	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		if (pageSize <= 0) {
			this.pageSize = 1;
		} else {
			this.pageSize = pageSize;
		}

	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	/**
	 * 设置总的数据个数
	 * 
	 * @author Du Haiying
	 * @param count 总的数据个数
	 */
	public void setCount(int count) {

		if (count <= 0) {
			this.count = 0;
			this.maxPageNo = 1;
			this.pageNo = 1;
		} else {
			this.count = count;
			this.maxPageNo = this.count % this.pageSize == 0 ? this.count / this.pageSize : this.count
					/ this.pageSize + 1;
		}
	}

	/**
	 * @return the datas
	 */
	public List<T> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @return the nextPageNo
	 */
	public int getNextPageNo() {

		if (pageNo == maxPageNo) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * @return the lastPageNo
	 */
	public int getLastPageNo() {
		if (pageNo == 1) {
			return 1;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 得到分页的第一个序列值
	 * 
	 * @author dhy
	 * @return
	 */
	public int getFirstIndex() {
		return (this.pageNo - 1) * pageSize;
	}

	/**
	 * @return the maxPageNo
	 */
	public int getMaxPageNo() {
		return maxPageNo;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasLast() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= maxPageNo);
	}

	/**
	 * 得到所有页码序列数组
	 * 
	 * @author Du Haiying
	 * @return
	 */
	public int[] getPageNos() {
		
		if(count <=0){
			return new int[0];
		}
		
		int[] pageNos = new int[this.maxPageNo];

		for (int i = 0; i < this.maxPageNo; i++) {
			pageNos[i] = i + 1;
		}
		return pageNos;

	}

}
