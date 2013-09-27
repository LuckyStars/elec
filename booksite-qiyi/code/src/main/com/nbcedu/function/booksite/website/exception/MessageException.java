package com.nbcedu.function.booksite.website.exception;


/**
 * 业务异常基类.
 *
 * @author bird
 */
@SuppressWarnings("serial")
public class MessageException extends RuntimeException {

	public MessageException() {
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}
}
