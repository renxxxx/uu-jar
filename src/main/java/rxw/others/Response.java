package rxw.others;

public class Response extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String errValue = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public static Response success() {
		return success(null, null);
	}

	public static Response success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static Response success(Object data) {
		return success(null, data);
	}

	public static Response success(String codeMsg, Object data) {
		Response response = new Response(0 + " : " + codeMsg);
		response.code = 0;
		response.codeMsg = codeMsg;
		response.data = data;
		return response;
	}

	public static Response failure() {
		return failure(null, null);
	}

	public static Response failure(String codeMsg) {
		return failure(codeMsg, null);
	}

	public static Response failure(Object data) {
		return failure(null, data);
	}

	public static Response failure(String codeMsg, Object data) {
		Response response = new Response(99 + " : " + codeMsg);
		response.code = 99;
		response.codeMsg = codeMsg;
		response.data = data;
		return response;
	}

	public static Response go(int code) {
		Response response = new Response(code + "");
		response.code = code;
		return response;
	}

	public static Response go(int code, String codeMsg, Object data) {
		Response response = new Response(code + " : " + codeMsg);
		response.code = code;
		response.codeMsg = codeMsg;
		response.data = data;
		return response;
	}

	public static Response go(int code, String codeMsg) {
		Response response = new Response(code + " : " + codeMsg);
		response.code = code;
		response.codeMsg = codeMsg;
		return response;
	}

	public static Response go(int code, Object data) {
		Response response = new Response(code + "");
		response.code = code;
		response.data = data;
		return response;
	}

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public Response(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public Response(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public Response(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public Response setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public Response setCodeMsgCn(String codeMsgCn) {
		this.codeMsgCn = codeMsgCn;
		return this;
	}

	public Response setCodeMsgEn(String codeMsgEn) {
		this.codeMsgEn = codeMsgEn;
		return this;
	}

	public String getCodeMsgEn() {
		return codeMsgEn;
	}

	public String getCodeMsgCn() {
		return codeMsgCn;
	}

	public String getDebug() {
		return debug;
	}

	public Response setDebug(String debug) {
		this.debug = debug;
		return this;
	}

	public Response setErrParam(String errParam) {
		this.errParam = errParam;
		return this;
	}

	public String getErrValue() {
		return errValue;
	}

	public Response setErrValue(String errValue) {
		this.errValue = errValue;
		return this;
	}

	public Response setCode(int code) {
		this.code = code;
		return this;
	}

	public Response setData(Object data) {
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

	public String getRequestId() {
		return requestId;
	}

	public Response setRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}

}
