package com.giveup;

public class CellReturn extends RuntimeException {
	protected int code = 99;
	protected String errParam;
	protected String codeMsg;
	protected Object data;

	public static CellReturn instance() {
		return new CellReturn();
	}

	public static CellReturn success(Object data) {
		return new CellReturn().setCode(0).setData(data);
	}

	public static CellReturn failure(String codeMsg) {
		return new CellReturn().setCode(99).setCodeMsg(codeMsg);
	}

	public static CellReturn failure(int code) {
		return new CellReturn().setCode(code);
	}

	public static CellReturn failure(int code, String codeMsg) {
		return new CellReturn().setCode(code).setCodeMsg(codeMsg);
	}

	private CellReturn() {
		super();
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public CellReturn setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public CellReturn setCode(int code) {
		this.code = code;
		return this;
	}

	public CellReturn setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public CellReturn setData(Object data) {
		this.data = data;
		return this;
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
