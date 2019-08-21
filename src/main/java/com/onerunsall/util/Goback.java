package com.onerunsall.util;

import com.alibaba.fastjson.JSONObject;

public class Goback extends RuntimeException {
	protected int code = 99;
	protected String errParam;
	protected String codeMsg;
	protected Object data = new JSONObject(true);

	public static Goback instance() {
		return new Goback();
	}

	public static Goback success() {
		return new Goback().setCode(0);
	}

	public static Goback success(Object data) {
		return new Goback().setCode(0).setData(data);
	}

	public static Goback failure(String codeMsg) {
		return new Goback().setCode(99).setCodeMsg(codeMsg);
	}

	public static Goback failure(int code) {
		return new Goback().setCode(code);
	}

	public static Goback failure(int code, String codeMsg) {
		return new Goback().setCode(code).setCodeMsg(codeMsg);
	}

	private Goback() {
		super();
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public Goback setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public Goback setCode(int code) {
		this.code = code;
		return this;
	}

	public Goback setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public Goback setData(Object data) {
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
