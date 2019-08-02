package com.giveup;

public class ErrorReturn extends RuntimeException {
	private int code = 99;
	private String errParam;
	private Object data;

	public ErrorReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorReturn(int code, String message) {
		super(message);
		this.code = code;
	}

	public ErrorReturn(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErrorReturn(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorReturn(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErrorReturn(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public ErrorReturn setCode(int code) {
		this.code = code;
		return this;
	}

	public String getErrParam() {
		return errParam;
	}

	public ErrorReturn setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ErrorReturn setData(Object data) {
		this.data = data;
		return this;
	}
}
