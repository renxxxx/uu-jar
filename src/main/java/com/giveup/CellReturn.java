package com.giveup;

public class CellReturn extends RuntimeException {
	private int code = 99;
	private String errParam;
	private String codeMsg;
	private Object data;

	public CellReturn() {
		super();
	}

	public CellReturn(int code) {
		this.code = code;
	}

	public CellReturn(int code, String codeMsg) {
		super(codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
	}

	public CellReturn(int code, Object data) {
		this.code = code;
		this.data = data;
	}

	public CellReturn(int code, String codeMsg, Object data) {
		super(codeMsg);
		this.code = code;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public CellReturn(int code, String codeMsg, String errParam, Object data) {
		super(codeMsg);
		this.code = code;
		this.errParam = errParam;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public CellReturn(int code, String codeMsg, String errParam) {
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
