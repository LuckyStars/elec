package com.nbcedu.function.elec.devcore.util.exl.process.builder;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.nbcedu.function.elec.devcore.util.exl.mapping.BookDataMapping;
import com.nbcedu.function.elec.devcore.util.exl.process.builder.style.ExlStyles;


public class ExlBuilder {
	/**
	 * when there is no data for export ,show this in the exl content
	 */
	private static final String NO_DATA_WARN = "没有符合条件的数据!";
	
	/**
	 * build the head and conditions
	 * @param head
	 * @param conditions
	 * @return
	 * @author xuechong
	 */
	public static Workbook buildEmptyWorkBook(String head,
			List<String> conditions,Workbook book,int sheetIndex) {
		buildHead(head,book);
		book = buildConditions(conditions, book,sheetIndex);
		return buildNoDataWarns(book,sheetIndex);
	}
	
	
	/**
	 * build the datacontents
	 * @param book
	 * @param data
	 * @return
	 * @author xuechong
	 */
	static Workbook buildDatas(Workbook book,BookDataMapping data,int sheetIndex){
		Sheet sheet = book.getSheetAt(sheetIndex);
		int curRowNum = sheet.getLastRowNum()+1;
		if (data.getTitles().size() > 0) {
			Row titleRow = sheet.createRow(curRowNum);
			Cell titleCell;
			//build the titles
			for (int i = 0, end = data.getTitles().size(); i < end; i++) {
				titleCell = titleRow.createCell(i);
				titleCell.setCellValue(data.getTitles().get(i));
				titleCell.setCellStyle(ExlStyles.getTitleStyle(book));
				expendColumnWidth(data.getTitles().get(i).length(), i, sheet);
			}
			//build the datacontent
			curRowNum++;
			if (data.getDatas().size() > 0) {
				Row dataRow;
				for (int i = 0, end = data.getDatas().size(); i < end; i++) {
					dataRow = sheet.createRow(curRowNum);
					curRowNum++;
					for (int j = 0,jend = data.getDatas().get(i).size(); j < jend; j++) {
						Cell dataCell = dataRow.createCell(j);
						dataCell.setCellStyle(ExlStyles.getCommonStyle(book));
						dataCell.setCellValue(data.getDatas().get(i).get(j));
						expendColumnWidth(data.getDatas().get(i).get(j).length(), j, sheet);
					}
				}
			}
		}
		return book;
	}

	/**
	 * 添加警告信息
	 * build the "there is no data" warn
	 * @param book
	 * @return
	 * @author xuechong
	 */
	private static Workbook buildNoDataWarns(Workbook book,int sheetIndex) {
		Sheet sheet = book.getSheetAt(sheetIndex);
		int row = sheet.getLastRowNum() + 1;
		sheet.addMergedRegion(createMergRegion(NO_DATA_WARN, row));
		Row curRow = sheet.createRow(row);
		Cell cell = curRow.createCell(0);
		cell.setCellStyle(ExlStyles.getHeadStyle(book));
		cell.setCellValue(NO_DATA_WARN);
		return book;
	}

	/**
	 * 创建头部信息
	 * @param head
	 * @return
	 * @author xuechong
	 */
	public static Workbook buildHead(String head,Workbook book){
		int curRow = 0;
		String headTrim = StringUtils.trimToNull(head);
		Sheet sheet = book.createSheet(headTrim == null ? "Workbook" : headTrim);
		if(headTrim!=null){
			// put headcontent
			Cell headCell = sheet.createRow(curRow).createCell(0);
			sheet.addMergedRegion(createMergRegion(headTrim, curRow));
			headCell.setCellStyle(ExlStyles.getHeadStyle(book));
			headCell.setCellValue(headTrim);
		}
		return book;
	}
	
	/**
	 * 构建查询条件信息
	 * @param conditions
	 * @param book
	 * @return
	 * @author xuechong
	 */
	public static Workbook buildConditions( List<String> conditions,Workbook book,int sheetIndex){
		Sheet sheet = book.getSheetAt(sheetIndex);
		int curRow = sheet.getLastRowNum();
		// put conditions
		if (conditions != null && !conditions.isEmpty()) {
			for (int i = 0, end = conditions.size(); i < end; i++) {
				if (i % 2 == 0) {// two conditions in each row
					curRow++;
					sheet.createRow(curRow);
				}
				// merg 3 Columns
				Cell conditionCell = sheet.getRow(curRow).createCell(
						i % 2 == 0 ? 0 : 3);
				sheet.addMergedRegion(new CellRangeAddress(curRow, curRow,
						conditionCell.getColumnIndex(), conditionCell.getColumnIndex() + 2));
				conditionCell.setCellStyle(ExlStyles.getConditionStyle(book));
				conditionCell.setCellValue(conditions.get(i));
			}
		}
		return book;
	}

	/**
	 * merg cal<br>
	 * cal the num of merg cloumns
	 * 
	 * @param contentStr
	 * @param row
	 * @return
	 * @author xuechong
	 */
	private static CellRangeAddress createMergRegion(String contentStr, Integer row) {
		int width = contentStr.length() / 2 + 1;
		return new CellRangeAddress(row, row, 0, width > 6 ? width : 6);
	}
	
	/**
	 * expend the column width
	 * @param dataLength
	 * @param columnIndex
	 * @param sheet
	 * @author xuechong
	 */
	private static void expendColumnWidth(int dataLength,int columnIndex,Sheet sheet){
		int expectLength = (dataLength<<9);
		if( dataLength > 4
				&&sheet.getColumnWidth(columnIndex) < expectLength){
			sheet.setColumnWidth(columnIndex,expectLength>255*256?(255*256):expectLength);
		}
	}
}
