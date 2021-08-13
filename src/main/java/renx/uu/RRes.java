package renx.uu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class RRes extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String msg = null;
	protected Object data = null;
	protected String reqNo = null;
	protected String msgEn = null;
	protected String msgCn = null;
	protected String error = null;

	public Map to() {
		Map map = new LinkedHashMap();
		map.put("reqNo", reqNo);
		map.put("msg", msg);
		map.put("code", code);
		map.put("data", data);
		map.put("errParam", errParam);
		return map;
	}

	public static RRes success() {
		return success(null, null);
	}

	public static RRes success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static RRes success(Object data) {
		return success(null, data);
	}

	public static RRes success(String msg, Object data) {
		RRes response = new RRes(0 + " : " + msg);
		response.code = 0;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static RRes fail() {
		return fail(null, null);
	}

	public static RRes fail(String codeMsg) {
		return fail(codeMsg, null);
	}

	public static RRes fail(Object data) {
		return fail(null, data);
	}

	public static RRes fail(String msg, Object data) {
		RRes response = new RRes(900 + " : " + msg);
		response.code = 900;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static RRes build(int code) {
		RRes response = new RRes(code + "");
		response.code = code;
		return response;
	}

	public static RRes build(int code, String msg, Object data) {
		RRes response = new RRes(code + " : " + msg);
		response.code = code;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static RRes build(int code, String msg) {
		RRes response = new RRes(code + " : " + msg);
		response.code = code;
		response.msg = msg;
		return response;
	}

	public static RRes build(int code, Object data) {
		RRes response = new RRes(code + "");
		response.code = code;
		response.data = data;
		return response;
	}

	public RRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RRes(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RRes(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RRes(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RRes(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getMsg() {
		return msg;
	}

	public RRes setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public RRes setMsgCn(String msgCn) {
		this.msgCn = msgCn;
		return this;
	}

	public RRes setMsgEn(String msgEn) {
		this.msgEn = msgEn;
		return this;
	}

	public String getMsgEn() {
		return msgEn;
	}

	public String getCodeMsgCn() {
		return msgCn;
	}

	public String getError() {
		return error;
	}

	public RRes setError(String error) {
		this.error = error;
		return this;
	}

	public RRes setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public RRes setCode(int code) {
		this.code = code;
		return this;
	}

	public RRes setData(Object data) {
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

	public String getReqNo() {
		return reqNo;
	}

	public RRes setReqNo(String reqNo) {
		this.reqNo = reqNo;
		return this;
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(RRes.fail("1212"), SerializerFeature.IgnoreNonFieldGetter));
	}
}
