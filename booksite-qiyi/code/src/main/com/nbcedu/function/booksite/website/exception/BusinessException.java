package com.nbcedu.function.booksite.website.exception;


/**
 * 业务异常基类.
 *
 * @author bird
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
}
