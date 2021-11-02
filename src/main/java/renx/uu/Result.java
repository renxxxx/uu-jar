package renx.uu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Result extends RuntimeException {

	protected String runNo = null;
	protected int code;
	protected String errorParam = null;
	protected String message = null;
	protected Object data = null;
	protected String messageEn = null;
	protected String messageCn = null;
	protected String error = null;

	public Map to() {
		Map map = new LinkedHashMap();
		map.put("runNo", runNo);
		map.put("message", message);
		map.put("code", code);
		map.put("data", data);
		map.put("errorParam", errorParam);
		return map;
	}

	public static Result success() {
		return success(null, null);
	}

	public static Result success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static Result success(Object data) {
		return success(null, data);
	}

	public static Result success(String message, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result response = new Result(0 + " : " + message);
		response.code = 0;
		response.message = message;
		response.data = data;
		return response;
	}

	public static Result fail() {
		return fail(null, null);
	}

	public static Result fail(String codeMsg) {
		return fail(codeMsg, null);
	}

	public static Result fail(Object data) {
		return fail(null, data);
	}

	public static Result fail(String message, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result response = new Result(900 + " : " + message);
		response.code = 900;
		response.message = message;
		response.data = data;
		return response;
	}

	public static Result build(int code) {
		Result response = new Result(code + "");
		response.code = code;
		return response;
	}

	public static Result build(int code, String message, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result response = new Result(code + " : " + message);
		response.code = code;
		response.message = message;
		response.data = data;
		return response;
	}

	public static Result build(int code, String message) {
		Result response = new Result(code + " : " + message);
		response.code = code;
		response.message = message;
		return response;
	}

	public static Result build(int code, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result response = new Result(code + "");
		response.code = code;
		response.data = data;
		return response;
	}

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public Result(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public Result(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public Result(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public Result msg(String msg) {
		this.message = message;
		return this;
	}

	public Result msgCn(String messageCn) {
		this.messageCn = messageCn;
		return this;
	}

	public Result messageEn(String messageEn) {
		this.messageEn = messageEn;
		return this;
	}

	public Result error(String error) {
		this.error = error;
		return this;
	}

	public Result errorParam(String errorParam) {
		this.errorParam = errorParam;
		return this;
	}

	public Result code(int code) {
		this.code = code;
		return this;
	}

	public Result data(Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		this.data = data;
		return this;
	}

	public Result runNo(String runNo) {
		this.runNo = runNo;
		return this;
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(Result.fail("1212"), SerializerFeature.IgnoreNonFieldGetter));
	}
}
