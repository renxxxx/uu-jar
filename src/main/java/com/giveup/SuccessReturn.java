package com.giveup;

public class SuccessReturn extends RuntimeException {
	private int code = 0;
	private Object data;

	public SuccessReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuccessReturn(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SuccessReturn(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SuccessReturn(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SuccessReturn(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public SuccessReturn setCode(int code) {
		this.code = code;
		return this;
	}

	public Object getData() {
		return data;
	}

	public SuccessReturn setData(Object data) {
		this.data = data;
		return this;
	}
}
