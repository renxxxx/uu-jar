package com.giveup;

public class WebPageReturn extends CellReturn {

	public WebPageReturn(int code) {
		super.code = code;
	}

	public WebPageReturn(int code, String codeMsg) {
		super.code = code;
		super.codeMsg = codeMsg;
	}

	public WebPageReturn(int code, Object data) {
		this.code = code;
		this.data = data;
	}

	public WebPageReturn(int code, String codeMsg, Object data) {
		this.code = code;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public WebPageReturn(int code, String codeMsg, String errParam, Object data) {
		this.code = code;
		this.errParam = errParam;
		this.data = data;
		this.codeMsg = codeMsg;
	}

	public WebPageReturn(int code, String codeMsg, String errParam) {
		this.code = code;
		this.errParam = errParam;
		this.codeMsg = codeMsg;
	}
	
	
}
