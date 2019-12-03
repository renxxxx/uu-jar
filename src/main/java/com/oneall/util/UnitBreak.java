package com.oneall.util;

public class UnitBreak extends RuntimeException {
	protected int code = 0;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public UnitBreak(int code) {
		super(code + "");
		this.code = code;
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

	public UnitBreak setCodeMsgCn(String codeMsgCn) {
		this.codeMsgCn = codeMsgCn;
		return this;
	}

	public UnitBreak setCodeMsgEn(String codeMsgEn) {
		this.codeMsgEn = codeMsgEn;
		return this;
	}

	public String getCodeMsgEn() {
		return codeMsgEn;
	}

	public String getCodeMsgCn() {
		return codeMsgCn;
	}

	public String getDebug() {
		return debug;
	}

	public UnitBreak setDebug(String debug) {
		this.debug = debug;
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

	public String getRequestId() {
		return requestId;
	}

	public UnitBreak setRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}

}
