package cc.renx.uu;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Result extends RuntimeException {

	public String runId = null;
	public int code;
	public String errorParam = null;
	public String msg = null;
	public Object data = new LinkedHashMap();
	public String enMsg = null;
	public String zhMsg = null;

	public Long submitDuration = null;
	public Long duration = null;
	public Long submitTime = null;
	public Long runTime = null;
	public Long responseTime = null;

	public Map to() {
		Map map = new LinkedHashMap();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", data);
		map.put("errorParam", errorParam);
		map.put("submitDuration", submitDuration);
		map.put("duration", duration);
		map.put("submitTime", submitTime);
		map.put("runTime", runTime);
		map.put("responseTime", responseTime);
		map.put("runId", runId);
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

		Object jj = data;
		if (data instanceof MMap)
			jj = ((MMap) jj).map;
		if (data instanceof LList)
			jj = ((LList) jj).list;
		if (jj != null)
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
		Object jj = data;
		if (data instanceof MMap)
			jj = ((MMap) jj).map;
		if (data instanceof LList)
			jj = ((LList) jj).list;
		if (jj != null)
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

	public Result duration(long duration) {
		this.duration = duration;
		return this;
	}

	public Result refreshDuration() {
		if (this.submitTime != null && this.runTime != null)
			this.submitDuration = this.runTime - this.submitTime;
		if (this.runTime != null && this.responseTime != null)
			this.duration = this.responseTime - this.runTime;
		return this;
	}

	public Result submitTime(long submitTime) {
		this.submitTime = submitTime;
		this.refreshDuration();
		return this;
	}

	public Result runTime(long runTime) {
		this.runTime = runTime;
		this.refreshDuration();
		return this;
	}

	public Result responseTime(long responseTime) {
		this.responseTime = responseTime;
		this.refreshDuration();
		return this;
	}

	public Result data(Object data) {
		Object jj = data;
		if (data instanceof MMap)
			jj = ((MMap) jj).map;
		if (data instanceof LList)
			jj = ((LList) jj).list;
		if (jj != null)
			this.data = data;
		return this;
	}

	public Result runId(String runId) {
		this.runId = runId;
		return this;
	}

	public static void throwFail(boolean is, String msg) {
		Result r = Result.fail(msg);
		if (is) {
			throw r;
		}
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(Result.fail("1212"), SerializerFeature.IgnoreNonFieldGetter));
	}
}
