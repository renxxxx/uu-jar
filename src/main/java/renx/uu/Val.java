package renx.uu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Val {
	private static Logger logger = LoggerFactory.getLogger(Val.class);

	public String name;
	public String code;
	public Object value;

	public static Val build(Object value) {
		Val val = new Val();
		val.value = value;
		return val;
	}
	
}
