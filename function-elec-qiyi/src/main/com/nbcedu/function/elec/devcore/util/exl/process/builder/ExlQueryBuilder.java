package com.nbcedu.function.elec.devcore.util.exl.process.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.nbcedu.function.elec.devcore.util.exl.mapping.BookDataMapping;


public class ExlQueryBuilder {
	
	/**
	 * build the workbook with the given datas
	 * @param head
	 * @param conditions
	 * @param datas
	 * @param queryString
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	public static Workbook buildQueryWorkBook(String head,
			List<String> conditions, List<Map> datas, String queryString) {
		Workbook book = new HSSFWorkbook();
		int sheetIndex = 0;
		ExlBuilder.buildHead(head,book);
		book = ExlBuilder.buildConditions(conditions, book,sheetIndex);
		
		BookDataMapping datasMapping = new BookDataMapping();
		List<String> titles = encapeTitles(queryString, datas.get(0).keySet());
		datasMapping.setTitles(titles);
		datasMapping.setDatas(encapeDatas(datas,titles));
		
		return ExlBuilder.buildDatas(book, datasMapping,sheetIndex);
	}
	
	/**
	 * encape the dataMaps to the List 
	 * @param datas
	 * @param titles
	 * @return
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	private static List<List<String>> encapeDatas(List<Map> datas,
			List<String> titles) {
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> data ;
		int datasize = titles.size();
		
		for (int i = 0,end = datas.size(); i < end; i++) {
			data = new ArrayList<String>(datasize);
			for (int j=0,jend=titles.size();j<jend;j++) {
				data.add(String.valueOf(datas.get(i).get(titles.get(j))));
			}
			result.add(data);
		}
		
		return result;
	}

	/**
	 * 对表头进行排序
	 * @param queryString
	 * @param titles
	 * @return
	 * @author xuechong
	 */
	private static List<String> encapeTitles(String queryString, Set<String> titles) {
		Map<Integer,String> temp = new TreeMap<Integer, String>();
		for (String title : titles) {
			temp.put(queryString.indexOf(title.toString()), title.toString());
		}
		if(temp.size()!= titles.size()){
			return new ArrayList<String>(titles);
		}
		return new ArrayList<String>(temp.values());
	}

}
