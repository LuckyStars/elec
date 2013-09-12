package com.nbcedu.function.elec.devcore.util.exl.process.builder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlData;
import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;
import com.nbcedu.function.elec.devcore.util.exl.mapping.BookDataMapping;
import com.nbcedu.function.elec.devcore.util.exl.mapping.SheetContent;



public class ExlAnnotationBuilder {
	/**
	 * build book 
	 * @param head
	 * @param conditions
	 * @param dataList
	 * @param viewType
	 * @return
	 * @author xuechong
	 */
	public static Workbook buildAnnoWorkBook(SheetContent content,Workbook book,int sheetIndex) {
		Integer viewType = content.getViewType();
		if(viewType==null){
			viewType = 0;
		}
		book = ExlBuilder.buildHead(content.getHead(),book);
		book = ExlBuilder.buildConditions(content.getConditions(), book,sheetIndex);
		BookDataMapping bookData = new BookDataMapping();
		bookData.setTitles(encapeTitles(content.getDataList().get(0),viewType));
		bookData.setDatas(encapeValue(content.getDataList(), viewType));
		ExlBuilder.buildDatas(book, bookData,sheetIndex);
		return book;
	}
	
	/**
	 * encape the titles
	 * @param data
	 * @param viewType
	 * @return
	 * @author xuechong
	 */
	private static List<String> encapeTitles(Object data,Integer viewType){
		List<String> result = null;
		Field[] fiedls = data.getClass().getDeclaredFields();
		Method[] methods = data.getClass().getDeclaredMethods();
		Map<Integer, String> titleMap = new TreeMap<Integer, String>();
		ExlData dataAnno;
		
		for (Field field : fiedls) {
			dataAnno = field.getAnnotation(ExlData.class);
			if(isDisplayValue(dataAnno, viewType)){
				titleMap.put(dataAnno.sortId(), dataAnno.title()[viewType]);
			}
		}
		
		for (Method getter : methods) {
			dataAnno = getter.getAnnotation(ExlData.class);
			if(isDisplayValue(dataAnno, viewType)){
				titleMap.put(dataAnno.sortId(), dataAnno.title()[viewType]);
			}
		}
		
		result = new ArrayList<String>(titleMap.values());
		return result;
	}
	/**
	 * put the data into the List<List<String>> 
	 * @param datas
	 * @param viewType
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	private static List<List<String>> encapeValue(List datas,Integer viewType){
		List<List<String>> result = new ArrayList<List<String>>();
		
		Map<Integer, String> dataMap = new TreeMap<Integer, String>();
		for (Object data : datas){
			if (!validateModel(data)) {
				throw new IllegalArgumentException("导出EXL时不支持的数据类型",
						new Throwable("no annotation " + ExlModel.class.getName() + " find on object"));
			}
			dataMap.clear();
			
			ExlData dataAnno;
			
			///put field values
			Field[] fields = data.getClass().getDeclaredFields();
			for (Field field : fields) {
				dataAnno = field.getAnnotation(ExlData.class);
				field.setAccessible(true);
				if(isDisplayValue(dataAnno, viewType)){//when title is empty means no to display in exl
					try {
						dataMap.put(dataAnno.sortId(), 
								field.get(data)!=null?field.get(data).toString():"");
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
			
			//put getter values
			Method[] methods = data.getClass().getDeclaredMethods();
			for (Method getter : methods) {
				dataAnno = getter.getAnnotation(ExlData.class);
				getter.setAccessible(true);
				if(isDisplayValue(dataAnno, viewType)){
					try {
						dataMap.put(dataAnno.sortId(),
								getter.invoke(data)!=null?getter.invoke(data).toString():"");
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(dataMap.size()>0){//if has values
				result.add(new ArrayList<String>(dataMap.values()));
			}
		}
		
		return result;
	}
	
	private static boolean isDisplayValue(ExlData dataAnno,Integer viewType){
		return dataAnno!=null&&StringUtils.isNotBlank(dataAnno.title()[viewType]);
	}
	/**
	 * validate if the model can be translate to exl<br>
	 * (wether the object has the {@link ExlModel} annotataion on its head)
	 * @param o
	 * @return
	 * @author xuechong
	 */
	private static boolean validateModel(Object o){
		return o.getClass().getAnnotation(ExlModel.class)!=null;
	}
	

}
