package com.giveup;

public class JsonReturn extends RuntimeException {
	protected int code = 99;
	protected String errParam;
	protected String codeMsg;
	protected Object data;

	public static JsonReturn success(Object data) {
		return new JsonReturn(0, data);
	}

	public static JsonReturn failure(String codeMsg) {
		return new JsonReturn(99, codeMsg);
	}

	public static JsonReturn failure(String codeMsg, Object data) {
		return new JsonReturn(99, codeMsg, data);
	}

	public JsonReturn() {
		super();
	}

	public JsonReturn(int code) {
		this.code = code;
	}

	public JsonReturn(int code, String codeMsg) {
		super(codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
	}

	public JsonReturn(int code, Object data) {
		this.code = code;
		this.data = data;
	}

	public JsonReturn(int code, String codeMsg, Object data) {
		super(codeMsg);
		this.code = code;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public JsonReturn(int code, String codeMsg, String errParam, Object data) {
		super(codeMsg);
		this.code = code;
		this.errParam = errParam;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public JsonReturn(int code, String codeMsg, String errParam) {
		super(codeMsg);
		this.code = code;
		this.errParam = errParam;
		this.codeMsg = codeMsg;
	}

	public int getCode() {
		return code;
	}

	public String getErrParam() {
		return errParam;
	}

	public Object getData() {
		return data;
	}

}
