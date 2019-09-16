package com.onerunsall.util;

public class UnitBreak extends RuntimeException {
	protected int code = 0;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String reqId = null;

	public UnitBreak(int code) {
		super(code + "");
		this.code = code;
	}

	public UnitBreak(String codeMsg) {
		super(99 + "-" + codeMsg);
		this.code = 99;
		this.codeMsg = codeMsg;
	}

	public UnitBreak(int code, String codeMsg) {
		super(code + "-" + codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
	}

	public UnitBreak(int code, String codeMsg, Object data) {
		super(code + "-" + codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
	}

	public UnitBreak(int code, Object data) {
		super(code + "");
		this.code = code;
		this.data = data;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public UnitBreak setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public UnitBreak setCode(int code) {
		this.code = code;
		return this;
	}

	public UnitBreak setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public UnitBreak setData(Object data) {
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

	public String getReqId() {
		return reqId;
	}

	public UnitBreak setReqId(String reqId) {
		this.reqId = reqId;
		return this;
	}

}
