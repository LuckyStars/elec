package com.nbcedu.function.elec.devcore.util.exl.mapping;

import java.util.List;

/**
 * the content of each sheet
 * @author xuechong
 *
 */
public class SheetContent {
	
	private final String head;
	private final List<String> conditions;
	@SuppressWarnings("unchecked")
	private final List dataList;
	private final Integer viewType;
	
	@SuppressWarnings("unchecked")
	public SheetContent(String head, List<String> conditions, List dataList,
			Integer viewType) {
		super();
		this.head = head;
		this.conditions = conditions;
		this.dataList = dataList;
		this.viewType = viewType;
	}
	public String getHead() {
		return head;
	}
	public List<String> getConditions() {
		return conditions;
	}
	@SuppressWarnings("unchecked")
	public List getDataList() {
		return dataList;
	}
	public Integer getViewType() {
		return viewType;
	}
	
}
