package com.nbcedu.function.booksite.website.pager;


public class Pager {

	private int offset=0;
	
	private int pagesize=10;
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	
	
}
