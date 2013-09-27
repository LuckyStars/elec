package com.nbcedu.function.booksite.website.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nbcedu.function.booksite.website.uitl.DateUtil;

public class CacheMapManager {
	
	static Map<String, CacheMap> cache = new HashMap<String, CacheMap>();
	
	/**
	 * 缓存数据
	 * @author 黎青春
	 * @param key
	 * @param object
	 */
	public static void put(String key,Object object){
		CacheMap cacheMap = new CacheMap();
		cacheMap.setKey(key);	
		cacheMap.setTimeOut(DateUtil.getDate(new Date(),5));	//设置过期时间
		cacheMap.setExpired(true);	//是否终止
		cacheMap.setValue(object);
		cache.put(key, cacheMap);
		System.out.println("添加");
	}
	/**
	 * 获取缓存数据
	 * @author 黎青春
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		try{
			CacheMap cacheMap = cache.get(key);
			
			System.out.println(cacheMap.getTimeOut());
			if(cacheMap!=null && cacheMap.getTimeOut()>0){
				if(cacheMap.isExpired()){
					if(cacheMap.getTimeOut()>new Date().getTime()){
						return cacheMap.getValue();
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				System.out.println("第1次---------");
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("第1次---------");
		}
		return null;
	}

}
