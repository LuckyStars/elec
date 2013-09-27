package com.nbcedu.function.booksite.website.pager;


public class SystemContext {
	@SuppressWarnings("unchecked")
	private static ThreadLocal offset = new ThreadLocal();
	@SuppressWarnings("unchecked")
	private static ThreadLocal pagesize = new ThreadLocal();
	
	@SuppressWarnings("unchecked")
	public static void setOffset(int _offset){
		offset.set(_offset);
	}
	
	public static int getOffset(){
		Integer os = (Integer)offset.get();
		if(os == null){
			return 0;
		}
		return os;
	}
	
	public static void removeOffset(){
		offset.remove();
	}
	
	@SuppressWarnings("unchecked")
	public static void setPagesize(int _pagesize){
		pagesize.set(_pagesize);
	}
	
	public static int getPagesize(){
		Integer ps = (Integer)pagesize.get();
		if(ps == null){
			return Integer.MAX_VALUE;
		}
		return ps;
	}
	
	public static void removePagesize(){
		pagesize.remove();
	}
}
