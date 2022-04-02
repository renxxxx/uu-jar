package renx.uu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Result extends RuntimeException {

	public String runNo = null;
	public int code;
	public String errorParam = null;
	public String msg = null;
	public Object data = new LinkedHashMap();
	public String enMsg = null;
	public String zhMsg = null;

	public Map to() {
		Map map = new LinkedHashMap();
		map.put("runNo", runNo);
		map.put("msg", msg);
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

	public static Result success(String msg, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result result = new Result(0 + " : " + msg);
		result.code = 0;
		result.msg = msg;
		result.data = data;
		return result;
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

	public static Result fail(String msg, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result result = new Result(9 + " : " + msg);
		result.code = 9;
		result.msg = msg;
		result.data = data;
		return result;
	}

	public static Result build(int code) {
		Result result = new Result(code + "");
		result.code = code;
		return result;
	}

	public static Result build(int code, String msg, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result result = new Result(code + " : " + msg);
		result.code = code;
		result.msg = msg;
		result.data = data;
		return result;
	}

	public static Result build(int code, String msg) {
		Result result = new Result(code + " : " + msg);
		result.code = code;
		result.msg = msg;
		return result;
	}

	public static Result build(int code, Object data) {
		if (data instanceof MMap)
			data = ((MMap) data).map;
		Result result = new Result(code + "");
		result.code = code;
		result.data = data;
		return result;
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
		this.msg = msg;
		return this;
	}

	public Result zhMsg(String zhMsg) {
		this.zhMsg = zhMsg;
		return this;
	}

	public Result enMsg(String enMsg) {
		this.enMsg = enMsg;
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
