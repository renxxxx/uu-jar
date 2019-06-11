package com.giveup;

public class InteractRuntimeException extends RuntimeException {
	private int code;
	private String errParam;
	private Object data;

	public InteractRuntimeException(int code) {
		super();
		this.code = code;
	}

	public InteractRuntimeException(String message, Object data) {
		super(message);
		this.code = 99;
		this.data = data;
	}

	public InteractRuntimeException(int code, String message, Object data) {
		super(message);
		this.code = code;
		this.data = data;
	}

	public InteractRuntimeException(int code, String message) {
		super(message);
		this.code = code;
	}

	public InteractRuntimeException(int code, String errParam, String message) {
		super(message);
		this.code = code;
		this.errParam = errParam;
	}

	public InteractRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.code = 99;
		// TODO Auto-generated constructor stub
	}

	public InteractRuntimeException(String message) {
		super(message);
		this.code = 99;
		// TODO Auto-generated constructor stub
	}

	public InteractRuntimeException(Throwable cause) {
		super(cause);
		this.code = 99;
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public String getErrParam() {
		return errParam;
	}

}
