package com.nbcedu.function.elec.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 星期
 * 
 * @author qinyuan
 * @since 2013-3-11
 */
public enum WeekType {
	MON(1, "周一"), TUE(2, "周二"), WED(3, "周三"), THU(4, "周四"), FRI(5, "周五"), SAT(6, "周六"), SUN(7, "周日");

	private int id;
	private String name;

	private WeekType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public static WeekType getById(int id) {
		for (WeekType type : values()) {
			if (type.id == id)
				return type;
		}

		return null;
	}

	public static Map<Integer, String> getEnumMap() {
		Map<Integer, String> enumMap = new HashMap<Integer, String>();
		for (WeekType type : values()) {
			enumMap.put(type.id, type.name);
		}

		return enumMap;
	}

	@Override
	public String toString() {
		return name() + "[" + this.id + " : " + this.name + "]";
	}

}