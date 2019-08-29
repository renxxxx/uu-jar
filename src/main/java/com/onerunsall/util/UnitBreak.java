package com.onerunsall.util;

public class UnitBreak extends RuntimeException {
	protected int code = 0;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;

	public static UnitBreak instance() {
		return new UnitBreak();
	}

	public static UnitBreak success() {
		return new UnitBreak().setCode(0);
	}

	public static UnitBreak success(Object data) {
		return new UnitBreak().setCode(0).setData(data);
	}

	public static UnitBreak failure(String codeMsg) {
		return new UnitBreak().setCode(99).setCodeMsg(codeMsg);
	}

	public static UnitBreak diy(int code) {
		return new UnitBreak().setCode(code);
	}

	public static UnitBreak diy(int code, String codeMsg) {
		return new UnitBreak().setCode(code).setCodeMsg(codeMsg);
	}

	public static UnitBreak diy(int code, String codeMsg, Object data) {
		return new UnitBreak().setCode(code).setCodeMsg(codeMsg).setData(data);
	}

	UnitBreak() {
		super();
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

}
