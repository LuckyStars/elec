package com.nbcedu.function.elec.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nbcedu.function.elec.vo.ECPrivilege;
import com.nbcedu.function.elec.vo.ECType;

public class DictionaryUtil {
	
	@SuppressWarnings("unchecked")
	private static Map<Class, ClassData> classMap = new HashMap<Class, ClassData>();
	private static final Gson gson  = new Gson();
	static{
		classMap.put(ECType.class, new ClassData("type.json", new TypeToken<List<ECType>>(){}.getType(), ECType.class));
		classMap.put(ECPrivilege.class, new ClassData("privilege.json", new TypeToken<List<ECPrivilege>>(){}.getType(), ECPrivilege.class));
		Operator.init();
	}
	/**
	 * get all type list
	 * @return
	 * @author xuechong
	 */
	public static List<ECType> getTypeList(){
		return Operator.getList(ECType.class);
	}
	/**
	 * get all type Map<br>
	 * key id , value ECType object
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,ECType> getTypeMap(){
		return Operator.getMap(ECType.class);
	}
	/**
	 * get ECType by id
	 * @param id
	 * @return
	 * @author xuechong
	 */
	public static ECType getTypeById(String id){
		return Operator.getObjectByIdClass(id, ECType.class);
	}
	/**
	 * get all type list
	 * @return
	 * @author xuechong
	 */
	public static List<ECPrivilege> getPrivilegeList(){
		return Operator.getList(ECPrivilege.class);
	}
	/**
	 * get all type Map<br>
	 * key id , value ECType object
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,ECPrivilege> getPrivilegeMap(){
		return Operator.getMap(ECPrivilege.class);
	}
	/**
	 * get Privilege by id
	 * @param id
	 * @return
	 * @author xuechong
	 */
	public static ECPrivilege getPrivilegeById(String id){
		return Operator.getObjectByIdClass(id, ECPrivilege.class);
	}
	
	/**
	 * the data container class
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	private static class ClassData{
		final String jsonPath ;
		final Type listType;
		@SuppressWarnings("unused")
		final Class objClass;
		Map mapDatas = null;
		List listDatas =null;
		ClassData(String jsonPath, Type listType, Class objClass) {
			super();
			this.jsonPath = jsonPath;
			this.listType = listType;
			this.objClass = objClass;
		}
	}
	private static class Operator{
		@SuppressWarnings("unchecked")
		private static void init(){
			for (Class clazz : classMap.keySet()) {
				String json = loadFile(classMap.get(clazz).jsonPath);
				List list = gson.fromJson(json, classMap.get(clazz).listType);
				classMap.get(clazz).listDatas = list;
				if(list!=null&&!list.isEmpty()){
					Map dataMap = new HashMap();
					for (Object object : list) {
						try {
							Method getId = object.getClass().getMethod("getId");
							String id = (String) getId.invoke(object);
							dataMap.put(id, object);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					classMap.get(clazz).mapDatas = dataMap;
				}
			}
		}
		/**
		 * get String from json file
		 * @param filePath
		 * @return
		 * @author xuechong
		 */
		private static String loadFile(String filePath){
			try {
				return FileUtils.readFileToString(
						new File(
								Thread.currentThread().getContextClassLoader().
								getResource(filePath).toURI()),"UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return "";
			}
		}
		/**
		 * get all data list
		 * @param <T>
		 * @param c
		 * @return
		 * @author xuechong
		 */
		@SuppressWarnings("unchecked")
		private static<T> List<T> getList(Class c){
			return Collections.unmodifiableList(classMap.get(c).listDatas);
	 	}
		@SuppressWarnings("unchecked")
		private static Map getMap(Class c){
			return Collections.unmodifiableMap(classMap.get(c).mapDatas);
	 	}
		@SuppressWarnings("unchecked")
		private static <T> T getObjectByIdClass(String id,Class c){
			return (T)classMap.get(c).mapDatas.get(id);
		}
	}
	
}
