package com.nbcedu.function.elec.devcore.util.exl.process;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.nbcedu.function.elec.devcore.util.exl.annotations.ExlModel;
import com.nbcedu.function.elec.devcore.util.exl.mapping.SheetContent;
import com.nbcedu.function.elec.devcore.util.exl.process.builder.ExlAnnotationBuilder;
import com.nbcedu.function.elec.devcore.util.exl.process.builder.ExlBuilder;
import com.nbcedu.function.elec.devcore.util.exl.process.writer.WorkBookWriter;


public class AnnotationResultProcessor {
	
	/**
	 * process the datas with Annotataions
	 * @throws IllegalArgumentException if there is no {@link ExlModel} Annotation on the data Objects
	 * @param head
	 * @param conditions
	 * @param dataList
	 * @param viewType
	 * @author xuechong
	 */
	@SuppressWarnings("unchecked")
	public static void process(String head,List<String> conditions,List dataList,Integer viewType){
		SheetContent content = new SheetContent(head, conditions, dataList, viewType);
		process(head, new SheetContent[]{content});
	}
	/**
	 * process the datas with Annotataions
	 * @throws IllegalArgumentException if there is no {@link ExlModel} Annotation on the data Objects
	 * @throws NullPointerException if the sheetContents is null or empty
	 * @param fileName
	 * @param sheetContents
	 * @author xuechong
	 */
	public static void process(String fileName,SheetContent[] sheetContents){
		Workbook book = new HSSFWorkbook();
		if(sheetContents==null||sheetContents.length<1){
			ExlBuilder.buildEmptyWorkBook(fileName, null, book, 0);
		}else{
			for (int i = 0;i<sheetContents.length;i++) {
				SheetContent content = sheetContents[i];
				if(content.getDataList()==null||content.getDataList().isEmpty()){
					ExlBuilder.buildEmptyWorkBook(content.getHead(), content.getConditions(),book, i);
				}else{
					ExlAnnotationBuilder.buildAnnoWorkBook(content, book, i);
				}
			}
		}
		
		WorkBookWriter.writeBook(book,fileName);
	}
	
	
	
}
