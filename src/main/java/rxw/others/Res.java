package rxw.others;

public class Res extends RuntimeException {

	protected int code;
	protected String errParam = null;
	protected String codeMsg = null;
	protected Object data = null;
	protected String requestId = null;
	protected String codeMsgEn = null;
	protected String codeMsgCn = null;
	protected String debug = null;

	public static Res success() {
		return success(null, null);
	}

	public static Res success(String codeMsg) {
		return success(codeMsg, null);
	}

	public static Res success(Object data) {
		return success(null, data);
	}

	public static Res success(String codeMsg, Object data) {
		Res response = new Res(0 + " : " + codeMsg);
		response.code = 0;
		response.codeMsg = codeMsg;
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

	public static Res failure(String codeMsg, Object data) {
		Res response = new Res(99 + " : " + codeMsg);
		response.code = 99;
		response.codeMsg = codeMsg;
		response.data = data;
		return response;
	}

	public static Res go(int code) {
		Res response = new Res(code + "");
		response.code = code;
		return response;
	}

	public static Res go(int code, String codeMsg, Object data) {
		Res response = new Res(code + " : " + codeMsg);
		response.code = code;
		response.codeMsg = codeMsg;
		response.data = data;
		return response;
	}

	public static Res go(int code, String codeMsg) {
		Res response = new Res(code + " : " + codeMsg);
		response.code = code;
		response.codeMsg = codeMsg;
		return response;
	}

	public static Res go(int code, Object data) {
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

	public String getCodeMsg() {
		return codeMsg;
	}

	public Res setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
		return this;
	}

	public Res setCodeMsgCn(String codeMsgCn) {
		this.codeMsgCn = codeMsgCn;
		return this;
	}

	public Res setCodeMsgEn(String codeMsgEn) {
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

	public String getRequestId() {
		return requestId;
	}

	public Res setRequestId(String requestId) {
		this.requestId = requestId;
		return this;
	}

}
