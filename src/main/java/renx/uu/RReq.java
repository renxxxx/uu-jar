package renx.uu;

import java.util.LinkedHashMap;
import java.util.Map;

public class RReq extends RuntimeException {

	private String requestId = null;
	private Map paramsMap = new LinkedHashMap();

}