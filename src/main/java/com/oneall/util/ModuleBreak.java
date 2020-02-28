package com.oneall.util;

public class ModuleBreak extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public static ModuleBreak success() {
		return success(null, null);
	}

	public static ModuleBreak success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static ModuleBreak success(Object data) {
		return success(null, data);
	}

	public static ModuleBreak success(String codeMsg, Object data) {
		ModuleBreak moduleBreak = new ModuleBreak(0, codeMsg);
		moduleBreak.code = 0;
		moduleBreak.codeMsg = codeMsg;
		moduleBreak.data = data;
		return moduleBreak;
	}

	public static ModuleBreak failure() {
		return failure(null, null);
	}

	public static ModuleBreak failure(String codeMsg) {
		return failure(codeMsg, null);
	}

	public static ModuleBreak failure(Object data) {
		return failure(null, data);
	}

	public static ModuleBreak failure(String codeMsg, Object data) {
		ModuleBreak moduleBreak = new ModuleBreak(99, codeMsg);
		moduleBreak.code = 99;
		moduleBreak.codeMsg = codeMsg;
		moduleBreak.data = data;
		return moduleBreak;
	}

	public ModuleBreak(int code) {
		super(code + "");
		this.code = code;
	}

	public ModuleBreak(int code, String codeMsg, Object data) {
		super(code + " - " + codeMsg);
		this.code = code;
		this.codeMsg = codeMsg;
		this.data = data;
	}

	public ModuleBreak(int code, String codeMsg) {
		super(code + " - " + codeMsg);
		this.code = code;
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
