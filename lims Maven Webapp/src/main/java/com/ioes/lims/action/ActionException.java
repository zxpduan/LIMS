package com.ioes.lims.action;

/**
 * @description: Action层异常类
 * @author: 杨奥
 * @time: 2016年1月5日下午6:19:07
 * @version: 0.0.1
 *
 */
public class ActionException extends RuntimeException{

	private static final long serialVersionUID = -5419168066185820572L;
	private Throwable cause;
	
	@Override
	public Throwable getCause() {
		return this.cause;
	}

	public ActionException() {
		super();
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
		this.cause=cause;
	}
	
}
