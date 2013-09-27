package com.nbcedu.function.booksite.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nbcedu.function.booksite.biz.BookSiteBiz;
import com.nbcedu.function.booksite.util.FusionChartUtil;
import com.nbcedu.function.booksite.vo.NameColor;
import com.nbcedu.function.booksite.website.action.WsBaseAction;

@SuppressWarnings("serial")
public class StatisticsAction extends WsBaseAction{
	protected static final Logger logger = Logger.getLogger(StatisticsAction.class);
	private String beginDate="",endDate="",type="",xml="";
	private BookSiteBiz bookSiteBiz;
	private List<NameColor> siteNameList = new ArrayList<NameColor>();

	@SuppressWarnings("unchecked")
	public String show() throws Exception {
		if("1".equals(type)){
			List list = bookSiteBiz.findSiteStatistics(beginDate,endDate);
			xml = FusionChartUtil.getXML_BySiteName(list, "场馆预定统计信息", "", "使用次数");
			encapeNameList(list);
		}else{
			List list = bookSiteBiz.findActivityStatistics(beginDate,endDate);
			xml = FusionChartUtil.getXML_ByActLevel(list, "活动级别统计信息", "级别名称", "使用次数");
		}
		return "show";
	}
	
	@SuppressWarnings("unchecked")
	private void encapeNameList(List<Map> data){
		if(data!=null&&data.size()>0){
			for(int i = 0,end = data.size();i<end;i++){
				NameColor nc = new NameColor();
				nc.setName(data.get(i).get("NAME").toString());
				nc.setColor(data.get(i).get("COLOR").toString());
				this.siteNameList.add(nc);	
				logger.info(i + ":" +nc.getName() + nc.getColor());
			}
		}
		
	}
	public String execute() throws Exception {
		return super.execute();
	}
	//////////////////////////
	//////GETTERS&SETTERS/////
	//////////////////////////
	public void setBookSiteBiz(BookSiteBiz bookSiteBiz) {
		this.bookSiteBiz = bookSiteBiz;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<NameColor> getSiteNameList() {
		return siteNameList;
	}
	public void setSiteNameList(List<NameColor> siteNameList) {
		this.siteNameList = siteNameList;
	}
}
