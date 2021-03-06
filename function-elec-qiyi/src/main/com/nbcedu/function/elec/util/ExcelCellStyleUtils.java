package com.nbcedu.function.elec.util;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelCellStyleUtils {
	// 标题样式  
    public static HSSFCellStyle titleStyle;  
    // 时间样式  
    public static HSSFCellStyle dataStyle;  
    // 单元格样式  
    public static HSSFCellStyle nameStyle;  
    // 超链接样式  
    public static HSSFCellStyle linkStyle;  
    public static HSSFFont font;  
  
    public ExcelCellStyleUtils(HSSFWorkbook workbook) {  
        titleStyle = titleStyle(workbook);  
        dataStyle = dataStyle(workbook);  
        nameStyle = nameStyle(workbook);  
        linkStyle = linkStyle(workbook);  
    }  
  
    /** 
     * 超链接样式 
     *  
     * @return HSSFCellStyle 
     */  
    private static HSSFCellStyle linkStyle(HSSFWorkbook work) {  
        HSSFCellStyle linkStyle = work.createCellStyle();  
        linkStyle.setBorderBottom((short) 1);  
        linkStyle.setBorderLeft((short) 1);  
        linkStyle.setBorderRight((short) 1);  
        linkStyle.setBorderTop((short) 1);  
        linkStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        linkStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        HSSFFont font = work.createFont();  
        font.setFontName(HSSFFont.FONT_ARIAL);  
        font.setUnderline((byte) 1);  
        font.setColor(HSSFColor.BLUE.index);  
        linkStyle.setFont(font);  
        return linkStyle;  
    }  
  
    /** 
     * s 单元格样式 
     *  
     * @return HSSFCellStyle 
     */  
    private static HSSFCellStyle nameStyle(HSSFWorkbook work) {  
        HSSFCellStyle nameStyle = work.createCellStyle();  
        nameStyle.setBorderBottom((short) 1);  
        nameStyle.setBorderLeft((short) 1);  
        nameStyle.setBorderRight((short) 1);  
        nameStyle.setBorderTop((short) 1);  
        nameStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);  
        nameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        return nameStyle;  
    }  
  
    /** 
     * 时间样式 
     *  
     * @return HSSFCellStyle 
     */  
    private static HSSFCellStyle dataStyle(HSSFWorkbook work) {  
        HSSFCellStyle dataStyle = work.createCellStyle();  
        dataStyle.setBorderBottom((short) 1);  
        dataStyle.setBorderLeft((short) 1);  
        dataStyle.setBorderRight((short) 1);  
        dataStyle.setBorderTop((short) 1);  
        dataStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);  
        dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        return dataStyle;  
    }  
  
    /** 
     * 标题样式 
     *  
     * @return HSSFCellStyle 
     */  
    private static HSSFCellStyle titleStyle(HSSFWorkbook work) {  
        HSSFCellStyle titleStyle = work.createCellStyle();  
        font = work.createFont();  
        font.setItalic(true);  
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)30); 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setColor(HSSFColor.BLUE.index);  
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);  
        titleStyle.setBorderLeft((short) 1);  
        titleStyle.setBorderRight((short) 1);  
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);  
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);  
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直  
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平   
        titleStyle.setFont(font);
        return titleStyle;  
    }  
}
