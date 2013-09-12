package com.nbcedu.function.elec.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class MysqlDialect extends MySQL5Dialect {
	public MysqlDialect() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());
	}
}
