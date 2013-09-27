package com.nbcedu.function.booksite.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 生成FusionChart所需数据的工具类
 * @author liqingchun
 *
 */
public class FusionChartUtil {
	/**
	 * 颜色代码
	 */
	private static String[] colors={"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526",
			"B3AA00","008ED6","9D080D","A186BE","CD3278","B4CDCD","B452CD","B22222","AEEEEE","ADFF2F","AAAAAA",
			"9BCD9B","9932CC","97FFFF","9370DB","8F8F8F","8B8B00","8B6914","8B0A50","00EE00","0000FF","00EEEE",
			"548B54","EE00EE","E9967A","EED2EE","F0FFF0","FFB5C5","FF6347","EE799F","C71585","C1FFC1"
	};
	//abandoned
	public static String getColor() {
        Random random = new Random();
        int pos_num = random.nextInt(40);
        return colors[pos_num];
    }
	
	
	
	/**
	 * 按活动级别生成数据XML
	 * @param datas
	 * @param caption
	 * @param xAxisName
	 * @param yAxisName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getXML_ByActLevel(List<Map> datas, String caption, String xAxisName, String yAxisName){
		StringBuffer xml = new StringBuffer("");
		xml.append("<graph caption='"+caption+"' ");
		xml.append(" xAxisName='"+xAxisName+"' ");
		xml.append(" yAxisName='"+yAxisName+"' ");
		xml.append(" decimalPrecision='0' formatNumberScale='0' baseFont='宋体' baseFontSize='12' > ");
		if(datas!=null && datas.size()>0){
			IndexFactory indexFac = new IndexFactory(colors.length);
			for(Map map : datas){
				xml.append("<set name='"+map.get("NAME").toString()+"' value='"+map.get("COUNT").toString()+"' color='"+colors[indexFac.getIndex()]+"' />");
			}
		}
		xml.append("</graph>");
		return xml.toString();
	}
	/**
	 * 按场馆名称生成数据XML
	 * @param datas
	 * @param caption
	 * @param xAxisName
	 * @param yAxisName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getXML_BySiteName(List<Map> datas, String caption, String xAxisName, String yAxisName){
		StringBuffer xml = new StringBuffer("");
		xml.append("<graph caption='"+caption+"' ");
		xml.append(" xAxisName='"+xAxisName+"' ");
		xml.append(" yAxisName='"+yAxisName+"' ");
		xml.append(" decimalPrecision='0' formatNumberScale='0' baseFont='宋体' baseFontSize='12' > ");
		if(datas!=null && datas.size()>0){
			IndexFactory indexFac = new IndexFactory(colors.length);
			for(int i = 0 ,end = datas.size();i<end;i++){
				datas.get(i).put("COLOR", colors[indexFac.getIndex()]);
				xml.append("<set name='' value='"+datas.get(i).get("COUNT").toString()+"' color='"+datas.get(i).get("COLOR")+"' />");
			}
		}
		xml.append("</graph>");
		return xml.toString();
	}
	
	/**
	 * 用于生成随机数的类<br>
	 * 每个对象的<b>getIndex()</b>方法可以以total为周期生成值在<b>0-total</b>范围内不重复的随机数
	 * @author xuechong
	 */
	private static class IndexFactory{//随即生成数字
		private Set<Integer> numSet;
		private Integer count;
		private Integer total;
		
		IndexFactory(Integer totalCount){
			this.total = totalCount;
			this.numSet = new HashSet<Integer>();
			this.count = 0;
		}
		/**
		 * 使用此方法获取生成的随机数
		 * @return
		 * @author xuechong
		 */
		Integer getIndex(){//获取随机数
			synchronized (this) {
				this.count ++;
				if (this.count > this.total ) {//如果超过一个周期,重置数据进入下一个周期
					this.numSet = new HashSet<Integer>();
					this.count = 1;
				}
				return generateIndex();
			}
		}
		
		private Integer generateIndex(){
			Random random = new Random();
			Integer index = random.nextInt(this.total);
			while (this.numSet.contains(index)) {
				index = random.nextInt(this.total);
			}
			this.numSet.add(index);
			return index;
		}
		
	}
	
	
}
