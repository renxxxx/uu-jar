package com.oneall.util;

public class ModuleBreak extends RuntimeException {

	protected int code = 0;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public ModuleBreak(int code, String codeMsg) {
		super(code + " - " + codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
	}

	public ModuleBreak(int code) {
		super(code + "");
		this.code = code;
	}

	public ModuleBreak() {
		super();
	}

	public ModuleBreak(String codeMsg) {
		super(99 + " - " + codeMsg);
		this.code = 99;
		this.codeMsg = codeMsg;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public ModuleBreak setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public ModuleBreak setCodeMsgCn(String codeMsgCn) {
		this.codeMsgCn = codeMsgCn;
		return this;
	}

	public ModuleBreak setCodeMsgEn(String codeMsgEn) {
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

	public ModuleBreak setDebug(String debug) {
		this.debug = debug;
		return this;
	}

	public ModuleBreak setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public ModuleBreak setData(Object data) {
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

	public ModuleBreak setRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}

}
