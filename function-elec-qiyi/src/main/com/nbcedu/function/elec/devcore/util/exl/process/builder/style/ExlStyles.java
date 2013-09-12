package com.nbcedu.function.elec.devcore.util.exl.process.builder.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * the styles of the export exlcells
 * @author xuechong
 */
public class ExlStyles {
	
	/**
	 * the style of the topHead
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	public static CellStyle getHeadStyle(Workbook wb) {
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setColor(IndexedColors.BLACK.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		return style;
	}
	
	/**
	 * the style of the titles
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	public static CellStyle getTitleStyle(Workbook wb){
		CellStyle style;
		Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createThickBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setWrapText(true);
        return style;
	}
	
	/**
	 * the style of the conditions and contents
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	public static CellStyle getCommonStyle(Workbook wb) {
		CellStyle style;
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * condition str styles
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	public static CellStyle getConditionStyle(Workbook wb){
		CellStyle style;
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * border styles
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
	
	/**
	 * border styles
	 * @param wb
	 * @return
	 * @author xuechong
	 */
	private static CellStyle createThickBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THICK);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THICK);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THICK);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THICK);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
	
}
