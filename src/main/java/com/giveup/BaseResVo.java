package com.giveup;

/**
 * 
 * @author rxw
 *
 */
public class BaseResVo {

	private String codeMsg = null;
	private String errParam = null;
	private String errMsg = null;
	private String serverVer = null;
	private Integer code = null;
	private String requestId = "";
	private Object data = new Object();

	public String getErrMsg() {
		return errMsg;
	}

	public String getErrParam() {
		return errParam;
	}

	public void setErrParam(String errParam) {
		this.errParam = errParam;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getServerVer() {
		return serverVer;
	}

	public void setServerVer(String serverVer) {
		this.serverVer = serverVer;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public Integer getCode() {
		return code;
	}

	public String getRequestId() {
		return requestId;
	}

	public Object getData() {
		return data;
	}

	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
