package com.nbcedu.function.elec.vo;

import java.util.Date;

public class ECClasshourVO {
	private String id;
	private Integer weekIndex; // 周
	private Date weekStartTime; // 周-开始时间
	private Date weekEndTime; // 周-结束时间
	
	private Integer pxOrder; // 序号
	
	
	public ECClasshourVO() {
	}
	
	
	public ECClasshourVO(Integer pxOrder, Integer weekIndex, Date weekStartTime, Date weekEndTime) {
		this.pxOrder = pxOrder;
		this.weekIndex = weekIndex;
		this.weekStartTime = weekStartTime;
		this.weekEndTime = weekEndTime;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getWeekIndex() {
		return weekIndex;
	}
	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}
	public Date getWeekStartTime() {
		return weekStartTime;
	}
	public void setWeekStartTime(Date weekStartTime) {
		this.weekStartTime = weekStartTime;
	}
	public Date getWeekEndTime() {
		return weekEndTime;
	}
	public void setWeekEndTime(Date weekEndTime) {
		this.weekEndTime = weekEndTime;
	}
	public Integer getPxOrder() {
		return pxOrder;
	}
	public void setPxOrder(Integer pxOrder) {
		this.pxOrder = pxOrder;
	}
	
	@Override
	public String toString() {
		return "ECClasshourVO [id=" + id + ", pxOrder=" + pxOrder + ", weekIndex=" + weekIndex + ", weekEndTime=" + weekEndTime
				+ ", weekStartTime=" + weekStartTime + "]";
	}
}
