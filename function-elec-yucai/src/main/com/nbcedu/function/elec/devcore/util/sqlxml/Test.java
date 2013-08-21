package com.nbcedu.function.elec.devcore.util.sqlxml;

import com.nbcedu.function.elec.devcore.util.sqlxml.context.SqlContext;

public class Test {
	public static void main(String[] args) {
		String sql = SqlContext.getByClass("bbb").getQueryById("list");
		System.out.println(sql);
	}
}
