package com.oneall.util;

public class ModuleResponse extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public static ModuleResponse success() {
		return success(null, null);
	}

	public static ModuleResponse success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static ModuleResponse success(Object data) {
		return success(null, data);
	}

	public static ModuleResponse success(String codeMsg, Object data) {
		ModuleResponse moduleBreak = new ModuleResponse(0 + " : " + codeMsg);
		moduleBreak.code = 0;
		moduleBreak.codeMsg = codeMsg;
		moduleBreak.data = data;
		return moduleBreak;
	}

	public static ModuleResponse failure() {
		return failure(null, null);
	}

	public static ModuleResponse failure(String codeMsg) {
		return failure(codeMsg, null);
	}

	public static ModuleResponse failure(Object data) {
		return failure(null, data);
	}

	public static ModuleResponse failure(String codeMsg, Object data) {
		ModuleResponse moduleBreak = new ModuleResponse(99 + " : " + codeMsg);
		moduleBreak.code = 99;
		moduleBreak.codeMsg = codeMsg;
		moduleBreak.data = data;
		return moduleBreak;
	}

	public static ModuleResponse response(int code) {
		ModuleResponse moduleBreak = new ModuleResponse(code + "");
		moduleBreak.code = code;
		return moduleBreak;
	}

	public static ModuleResponse response(int code, String codeMsg, Object data) {
		ModuleResponse moduleBreak = new ModuleResponse(code + " : " + codeMsg);
		moduleBreak.code = code;
		moduleBreak.codeMsg = codeMsg;
		moduleBreak.data = data;
		return moduleBreak;
	}

	public static ModuleResponse response(int code, String codeMsg) {
		ModuleResponse moduleBreak = new ModuleResponse(code + " : " + codeMsg);
		moduleBreak.code = code;
		moduleBreak.codeMsg = codeMsg;
		return moduleBreak;
	}

	public static ModuleResponse response(int code, Object data) {
		ModuleResponse moduleBreak = new ModuleResponse(code + "");
		moduleBreak.code = code;
		moduleBreak.data = data;
		return moduleBreak;
	}
//	private ModuleResponse(int code) {
//		super(code + "");
//		this.code = code;
//	}
//
//	private ModuleResponse(int code, String codeMsg, Object data) {
//		super(code + " - " + codeMsg);
//		this.code = code;
//		this.codeMsg = codeMsg;
//		this.data = data;
//	}
//
//	private ModuleResponse(int code, String codeMsg) {
//		super(code + " - " + codeMsg);
//		this.code = code;
//		this.codeMsg = codeMsg;
//	}

	public ModuleResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModuleResponse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ModuleResponse(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ModuleResponse(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ModuleResponse(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public ModuleResponse setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public ModuleResponse setCodeMsgCn(String codeMsgCn) {
		this.codeMsgCn = codeMsgCn;
		return this;
	}

	public ModuleResponse setCodeMsgEn(String codeMsgEn) {
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

	public ModuleResponse setDebug(String debug) {
		this.debug = debug;
		return this;
	}

	public ModuleResponse setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public ModuleResponse setData(Object data) {
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

	public ModuleResponse setRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}

}
