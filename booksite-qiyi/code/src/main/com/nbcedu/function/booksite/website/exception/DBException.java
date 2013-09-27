package com.nbcedu.function.booksite.website.exception;

/**
 * 数据库操作异常类
 * 
 * @author Xiedayun
 * 
 */
public class DBException extends Exception {
	
	private static final long serialVersionUID = 300047674808718515L;
	
	public DBException() {
		super();
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
}
