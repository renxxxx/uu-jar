package renx.archive;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Res extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String msg = null;
	protected Object data = null;
	protected String reqNo = null;
	protected String msgEn = null;
	protected String msgCn = null;
	protected String debug = null;

	public Map to() {
		Map map = new LinkedHashMap();
		map.put("reqNo", reqNo);
		map.put("msg", msg);
		map.put("code", code);
		map.put("data", data);
		map.put("errParam", errParam);
		return map;
	}

	public static Res success() {
		return success(null, null);
	}

	public static Res success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static Res success(Object data) {
		return success(null, data);
	}

	public static Res success(String msg, Object data) {
		Res response = new Res(0 + " : " + msg);
		response.code = 0;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static Res failure() {
		return failure(null, null);
	}

	public static Res failure(String codeMsg) {
		return failure(codeMsg, null);
	}

	public static Res failure(Object data) {
		return failure(null, data);
	}

	public static Res failure(String msg, Object data) {
		Res response = new Res(900 + " : " + msg);
		response.code = 900;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static Res build(int code) {
		Res response = new Res(code + "");
		response.code = code;
		return response;
	}

	public static Res build(int code, String msg, Object data) {
		Res response = new Res(code + " : " + msg);
		response.code = code;
		response.msg = msg;
		response.data = data;
		return response;
	}

	public static Res build(int code, String msg) {
		Res response = new Res(code + " : " + msg);
		response.code = code;
		response.msg = msg;
		return response;
	}

	public static Res build(int code, Object data) {
		Res response = new Res(code + "");
		response.code = code;
		response.data = data;
		return response;
	}

	public Res() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Res(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public Res(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public Res(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public Res(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getMsg() {
		return msg;
	}

	public Res setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Res setMsgCn(String msgCn) {
		this.msgCn = msgCn;
		return this;
	}

	public Res setMsgEn(String msgEn) {
		this.msgEn = msgEn;
		return this;
	}

	public String getMsgEn() {
		return msgEn;
	}

	public String getCodeMsgCn() {
		return msgCn;
	}

	public String getDebug() {
		return debug;
	}

	public Res setDebug(String debug) {
		this.debug = debug;
		return this;
	}

	public Res setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public Res setCode(int code) {
		this.code = code;
		return this;
	}

	public Res setData(Object data) {
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

	public Res setReqNo(String reqNo) {
		this.reqNo = reqNo;
		return this;
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(Res.failure("1212"), SerializerFeature.IgnoreNonFieldGetter));
	}
}
