package com.onerunsall.util;

public class UnitRes<T> {
	protected int code = 0;
	protected String reqId = null;
	protected String errParam = null;
	protected String codeMsg = null;
	protected T data = null;

	UnitRes() {
		super();
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public UnitRes<T> setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public UnitRes<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public UnitRes<T> setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getErrParam() {
		return errParam;
	}

	public T getData() {
		return data;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public UnitRes<T> setData(Object data) {
		this.data = (T) data;
		return this;
	}

	@Override
	public String toString() {
		return "UnitRes [code=" + code + ", reqId=" + reqId + ", errParam=" + errParam + ", codeMsg=" + codeMsg
				+ ", data=" + data + "]";
	}

}
