package com.nbcedu.function.elec.util;


/**
 * 课程属性(育才)
 * 
 * @author qinyuan
 * @since 2013-3-19
 */
public enum CourseAttr {
	BasedCourse(1, "校本课程"), ResearchCourse(2, "研究性学习"), AllCourse(3, "研究性学习与校本课程");

	private int id;
	private String name;

	private CourseAttr(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public static CourseAttr getById(int id) {
		for (CourseAttr type : values()) {
			if (type.id == id)
				return type;
		}

		return null;
	}

	@Override
	public String toString() {
		return name() + "[" + this.id + " : " + this.name + "]";
	}

}