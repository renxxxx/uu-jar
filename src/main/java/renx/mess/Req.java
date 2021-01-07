package renx.mess;

import java.util.LinkedHashMap;
import java.util.Map;

public class Req extends RuntimeException {

	private String requestId = null;
	private Map paramsMap = new LinkedHashMap();

}
